package com.podcase.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.podcase.serializer.EpisodeSerializer;

@Entity
public class DeadDownload {
	
	@Id
	@GeneratedValue(generator = "deadDownload_generator")
	@SequenceGenerator(name = "deadDownload_generator", sequenceName = "deadDownload_sequence", initialValue = 1)
	Long id;
	
	Integer attemptCount;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date lastDownloadAttempt;
	
	@OneToOne
    @JoinColumn(name = "episode_id", referencedColumnName = "id")
	@JsonSerialize(using = EpisodeSerializer.class)
	Episode episode;
	
	public DeadDownload() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAttemptCount() {
		return attemptCount;
	}

	public void setAttemptCount(Integer attemptCount) {
		this.attemptCount = attemptCount;
	}

	public Episode getEpisode() {
		return episode;
	}

	public void setEpisode(Episode episode) {
		this.episode = episode;
	}

	public Date getLastDownloadAttempt() {
		return lastDownloadAttempt;
	}

	public void setLastDownloadAttempt(Date lastDownloadAttempt) {
		this.lastDownloadAttempt = lastDownloadAttempt;
	}

}
