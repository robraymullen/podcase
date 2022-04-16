package com.podcase.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Field;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.podcase.serializer.PlayStateSerializer;

@Entity
@JsonSerialize(using = PlayStateSerializer.class)
public class PlayState {
	
	@Id
	@GeneratedValue(generator = "playState_generator")
	@SequenceGenerator(name = "playState_generator", sequenceName = "playState_sequence", initialValue = 1)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	User user;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "episode_id", referencedColumnName = "id")
	Episode episode;
	
	@Field
	Long playLength;
	
	@Field
	Boolean played;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date lastPlayed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Episode getEpisode() {
		return episode;
	}

	public void setEpisode(Episode episode) {
		this.episode = episode;
	}

	public Long getPlayLength() {
		return playLength;
	}

	public void setPlayLength(Long playLength) {
		this.playLength = playLength;
	}

	public Boolean isPlayed() {
		return played;
	}

	public void setPlayed(Boolean played) {
		this.played = played;
	}

	public Date getLastPlayed() {
		return lastPlayed;
	}

	public void setLastPlayed(Date lastPlayed) {
		this.lastPlayed = lastPlayed;
	}
}
