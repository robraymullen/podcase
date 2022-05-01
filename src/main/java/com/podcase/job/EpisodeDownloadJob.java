package com.podcase.job;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.Base64;
import java.util.Date;
import java.util.Deque;
import java.util.Optional;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.podcase.model.DeadDownload;
import com.podcase.model.Episode;
import com.podcase.model.Podcast;
import com.podcase.repository.DeadDownloadRepository;
import com.podcase.repository.EpisodeRepository;
import com.podcase.utilities.EpisodeFileCleaner;

@Service
@Qualifier("downloadJob")
public class EpisodeDownloadJob implements ScheduledJob {
	
	private static int MAX_DOWNLOAD_ATTEMPTS = 10;
	
	Logger logger = LoggerFactory.getLogger(EpisodeDownloadJob.class);
	
	@Value("${audio.file.store}")
	private String audioStore;
	
	private Deque<Episode> downloadQueue = new ArrayDeque<Episode>();
	
	EpisodeRepository repository;

	private EpisodeFileCleaner fileCleaner;

	private DeadDownloadRepository deadDownloadRepository;
	
	@Autowired
	public EpisodeDownloadJob(EpisodeRepository repository, DeadDownloadRepository deadDownloadRepository, EpisodeFileCleaner fileCleaner) {
		this.repository = repository;
		this.deadDownloadRepository = deadDownloadRepository;
		this.fileCleaner = fileCleaner;
	}
	
	@Override
	public void process() {
		downloadQueue.addAll(repository.findByDownloaded(false));
		while (!downloadQueue.isEmpty()) {
			Episode episode = downloadQueue.poll();
			Podcast podcast = episode.getPodcast();
			
			Optional<DeadDownload> download = this.deadDownloadRepository.findByEpisodeId(episode.getId());
			if (download.isPresent()) {
				if (download.get().getAttemptCount() >= MAX_DOWNLOAD_ATTEMPTS) {
					logger.warn("Skipping download attempt for episode "+episode.getId()+" with name "+episode.getTitle()+ " as download attemtps has exceeded the limit");
					continue;
				}
			}
			
			try {
				String podcastDirectory = System.getProperty("user.dir")+audioStore+podcast.getName().replaceAll("\\s+","");
				File podcastDirFile = new File(podcastDirectory);
				Files.createDirectories(podcastDirFile.toPath());
				String fileName = fileCleaner.getFileName(episode);
				String filePath = podcastDirectory+"/"+fileName;
				episode.setFileName(fileName);
				episode.setFilePath(filePath);
				// Handle possible https redirects
				URLConnection con = new URL( episode.getFileUrl() ).openConnection();
				con.connect();
				InputStream is = con.getInputStream();
				URL endpointURL;
				if (con.getHeaderField("Location") != null) {
					endpointURL = new URL(con.getHeaderField("Location"));
				} else {
					endpointURL = new URL( episode.getFileUrl());
				}
				
				is.close();
				File destination = new File(filePath);
				FileUtils.copyURLToFile(endpointURL, destination);
				if (episode.getDuration() == null || episode.getDuration() == 0) {
					AudioFile audioFile = AudioFileIO.read(destination);
					AudioHeader header = audioFile.getAudioHeader();
					episode.setDuration(header.getTrackLength());
//					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(destination);
//					AudioFormat format = audioInputStream.getFormat();
//					long frames = audioInputStream.getFrameLength();
//					double durationInSeconds = (frames+0.0) / format.getFrameRate(); 
//					episode.setDuration(Math.toIntExact(Math.round(durationInSeconds)));
				}
				episode.setDownloaded(true);
				repository.save(episode);
				if (download.isPresent()) {
					deadDownloadRepository.delete(download.get());
				}
			} catch (Exception e) {
				DeadDownload deadDownload;
				if (download.isPresent()) {
					deadDownload = download.get();
					deadDownload.setAttemptCount(deadDownload.getAttemptCount() + 1);
				} else {
					deadDownload = new DeadDownload();
					deadDownload.setEpisode(episode);
					deadDownload.setAttemptCount(1);
				}
				deadDownload.setLastDownloadAttempt(new Date());
				deadDownloadRepository.save(deadDownload);
				logger.error(e.getMessage(), e);
			}
		}
	}

}
