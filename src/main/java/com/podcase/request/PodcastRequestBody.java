package com.podcase.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PodcastRequestBody {

	@JsonProperty("podcastUrl")
	private String podcastUrl;
	
	public PodcastRequestBody() {
		
	}
	
	public PodcastRequestBody(String podcastUrl) {
		this.podcastUrl = podcastUrl;
	}
	
	@JsonProperty("podcastUrl")
	public String getPodcastUrl() {
		return podcastUrl;
	}
	
	@JsonProperty("podcastUrl")
	public void setPodcastUrl(String podcastUrl) {
		this.podcastUrl = podcastUrl;
	}
	
}
