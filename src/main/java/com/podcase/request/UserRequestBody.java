package com.podcase.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequestBody {
	
	@JsonProperty("name")
	String name;
	
	@JsonProperty("password")
	String password;
	
	@JsonProperty("subscription")
	String subscriptionName;
	
	@JsonProperty("imageUrl")
	String imageUrl;
	
	public UserRequestBody() {
		
	}
	
	public UserRequestBody(String name, String password) {
		this.name = name;
		this.password = password;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonProperty("subscription")
	public String getSubscriptionName() {
		return subscriptionName;
	}

	@JsonProperty("subscription")
	public void setSubscriptionName(String podcastName) {
		this.subscriptionName = podcastName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
