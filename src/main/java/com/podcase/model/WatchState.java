package com.podcase.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.search.annotations.Field;

@Entity
public class WatchState {
	
	@Id
	@GeneratedValue(generator = "watchState_generator")
	@SequenceGenerator(name = "watchState_generator", sequenceName = "watchState_sequence", initialValue = 1)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	User user;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "episode_id", referencedColumnName = "id")
	Episode episode;
	
	@Field
	Long watchedLength;
	
	@Field
	Boolean watched;

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

	public Long getWatchedLength() {
		return watchedLength;
	}

	public void setWatchedLength(Long watchedLength) {
		this.watchedLength = watchedLength;
	}

	public Boolean getWatched() {
		return watched;
	}

	public void setWatched(Boolean watched) {
		this.watched = watched;
	}
	

}
