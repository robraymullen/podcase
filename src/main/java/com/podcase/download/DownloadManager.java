package com.podcase.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
@Qualifier("downloadManager")
public class DownloadManager {
	
	@Value("${audio.file.store}")
	private String audioStore;
	
	private Deque<Episode> downloadQueue = new ArrayDeque<Episode>();
	
	EpisodeRepository repository;
	
	@Autowired
	public DownloadManager(EpisodeRepository repository) {
		this.repository = repository;
	}
	
	public void startDownload() {
		downloadQueue.addAll(repository.findByDownloaded(false));
		while (!downloadQueue.isEmpty()) {
			Episode episode = downloadQueue.poll();
			try {
				String[] splitFileUrl = episode.getFileUrl().split("/");
				String fileName = splitFileUrl[splitFileUrl.length - 1];
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
				FileUtils.copyURLToFile(
						endpointURL, 
						  new File(System.getProperty("user.dir")+audioStore+fileName));
				episode.setDownloaded(true);
				repository.save(episode);
			} catch (Exception e) {
				downloadQueue.add(episode); //Terrible idea, will loop endlessly on error
				e.printStackTrace();
			}
		}
	}

}
