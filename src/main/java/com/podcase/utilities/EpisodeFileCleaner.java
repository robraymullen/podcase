package com.podcase.utilities;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.podcase.model.Episode;

@Service
public class EpisodeFileCleaner {
	
	public EpisodeFileCleaner() {
		
	}
	
	public String getFileName(Episode episode) {
		String[] splitFileUrl = episode.getFileUrl().split("/");
		String remoteFileName = splitFileUrl[splitFileUrl.length - 1].split("\\?")[0];
		String guidForFileName = "";
		if (episode.getGuid().contains("/")) {
			guidForFileName = Base64.getEncoder().encodeToString(episode.getGuid().getBytes());
		} else {
			guidForFileName = episode.getGuid();
		}
		return guidForFileName + "_"+remoteFileName; //A lot of podcasts use non-unique names for files!
	}

}
