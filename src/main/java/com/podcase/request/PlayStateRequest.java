package com.podcase.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayStateRequest {

	@JsonProperty("userId")
	Long userId;
	
	@JsonProperty("episodeId")
	Long episodeId;
	
	@JsonProperty("playStateId")
	Long playStateId;
	
	@JsonProperty("playLength")
	Long playLength;
	
	@JsonProperty("played")
	boolean played;
	
	@JsonProperty("lastPlayed")
	Long lastPlayed;

	@JsonProperty("userId")
	public Long getUserId() {
		return userId;
	}

	@JsonProperty("userId")
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@JsonProperty("episodeId")
	public Long getEpisodeId() {
		return episodeId;
	}

	@JsonProperty("episodeId")
	public void setEpisodeId(Long episodeId) {
		this.episodeId = episodeId;
	}

	@JsonProperty("playStateId")
	public Long getPlayStateId() {
		return playStateId;
	}

	@JsonProperty("playStateId")
	public void setPlayStateId(Long playStateId) {
		this.playStateId = playStateId;
	}

	@JsonProperty("playLength")
	public Long getPlayLength() {
		return playLength;
	}

	@JsonProperty("playLength")
	public void setPlayLength(Long playLength) {
		this.playLength = playLength;
	}

	@JsonProperty("played")
	public boolean isPlayed() {
		return played;
	}

	@JsonProperty("played")
	public void setPlayed(boolean played) {
		this.played = played;
	}

	@JsonProperty("lastPlayed")
	public Long getLastPlayed() {
		return lastPlayed;
	}

	@JsonProperty("lastPlayed")
	public void setLastPlayed(Long lastPlayed) {
		this.lastPlayed = lastPlayed;
	}
}
