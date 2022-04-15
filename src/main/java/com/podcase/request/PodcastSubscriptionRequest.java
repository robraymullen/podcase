package com.podcase.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PodcastSubscriptionRequest {

	@JsonProperty("podcastUrl")
	private String podcastUrl;
	
	@JsonProperty("podcastName")
	private String podcastName;
	
	@JsonProperty("userName")
	private String userName;
	
	public PodcastSubscriptionRequest() {
		
	}
	
	public PodcastSubscriptionRequest(String podcastUrl, String podcastName, String userName) {
		this.podcastUrl = podcastUrl;
		this.podcastName = podcastName;
		this.userName = userName;
	}
	
	@JsonProperty("podcastUrl")
	public String getPodcastUrl() {
		return podcastUrl;
	}
	
	@JsonProperty("podcastUrl")
	public void setPodcastUrl(String podcastUrl) {
		this.podcastUrl = podcastUrl;
	}

	@JsonProperty("podcastName")
	public String getPodcastName() {
		return podcastName;
	}

	@JsonProperty("podcastName")
	public void setPodcastName(String podcastName) {
		this.podcastName = podcastName;
	}

	@JsonProperty("userName")
	public String getUserName() {
		return userName;
	}

	@JsonProperty("userName")
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
