package com.podcase.job;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayDeque;
import java.util.Base64;
import java.util.Deque;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.podcase.model.Episode;
import com.podcase.repository.EpisodeRepository;

@Service
@Qualifier("downloadJob")
public class EpisodeDownloadJob implements ScheduledJob {
	
	@Value("${audio.file.store}")
	private String audioStore;
	
	private Deque<Episode> downloadQueue = new ArrayDeque<Episode>();
	
	EpisodeRepository repository;
	
	@Autowired
	public EpisodeDownloadJob(EpisodeRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void process() {
		downloadQueue.addAll(repository.findByDownloaded(false));
		while (!downloadQueue.isEmpty()) {
			Episode episode = downloadQueue.poll();
			try {
				String[] splitFileUrl = episode.getFileUrl().split("/");
				String guidForFileName = "";
				if (episode.getGuid().contains("/")) {
					guidForFileName = Base64.getEncoder().encodeToString(episode.getGuid().getBytes());
				} else {
					guidForFileName = episode.getGuid();
				}
				String fileName = guidForFileName + "_"+splitFileUrl[splitFileUrl.length - 1]; //A lot of podcasts use non-unique names for files!
				String filePath = System.getProperty("user.dir")+audioStore+fileName;
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
			} catch (Exception e) {
				//TODO setup 'dead download' queue
				e.printStackTrace();
			}
		}
	}

}
