package com.podcase.job;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.commons.io.FileUtils;
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
//		downloadQueue.addAll(repository.findByDownloaded(false));
//		while (!downloadQueue.isEmpty()) {
//			Episode episode = downloadQueue.poll();
//			try {
//				String[] splitFileUrl = episode.getFileUrl().split("/");
//				String fileName = episode.getGuid() + "_"+splitFileUrl[splitFileUrl.length - 1]; //A lot of podcasts use non-unique names for files!
//				String filePath = System.getProperty("user.dir")+audioStore+fileName;
//				episode.setFileName(fileName);
//				episode.setFilePath(filePath);
//				// Handle possible https redirects
//				URLConnection con = new URL( episode.getFileUrl() ).openConnection();
//				con.connect();
//				InputStream is = con.getInputStream();
//				URL endpointURL;
//				if (con.getHeaderField("Location") != null) {
//					endpointURL = new URL(con.getHeaderField("Location"));
//				} else {
//					endpointURL = new URL( episode.getFileUrl());
//				}
//				
//				is.close();
//				FileUtils.copyURLToFile(endpointURL, new File(filePath));
//				episode.setDownloaded(true);
//				repository.save(episode);
//			} catch (Exception e) {
//				//TODO setup 'dead download' queue
//				e.printStackTrace();
//			}
//		}
	}

}
