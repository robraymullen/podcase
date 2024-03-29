package com.podcase.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Podcast {

	@Id
	@GeneratedValue(generator = "podcast_generator")
	@SequenceGenerator(name = "podcast_generator", sequenceName = "podcast_sequence", initialValue = 1)
	private Long id;

	@NotBlank
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	@Size(max = 4000)
	private String name;
	
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	@Size(max = 4000)
	private String author;

	@NotBlank
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	@Size(max = 4000)
	private String link;

	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	@Size(max = 4000)
	private String rssFeed;
	
	@Field
	@Size(max = 4000)
	private String imageUrl;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_build_date")
	private Date lastBuildDate;

	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	@Size(max = 4000)
	private String description;
	
//	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "podcast_id")
	List<Episode> episodes = new ArrayList<>();
	
	@Transient
	Set<String> episodeGuids = new HashSet<>();
	
	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return "Podcast [id=" + id + ", name=" + name + ", author=" + author + ", link=" + link + ", rssFeed=" + rssFeed
				+ ", imageUrl=" + imageUrl + ", lastBuildDate=" + lastBuildDate + ", description=" + description
				+ ", episodeGuids=" + episodeGuids + "]";
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return this.link;
	}

	public void setRssFeed(String rssFeed) {
		this.rssFeed = rssFeed;
	}

	public String getRssFeed() {
		return this.rssFeed;
	}

	public void setLastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public Date getLastBuildDate() {
		return this.lastBuildDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}
	
	public void addEpisode(Episode episode) {
		episodes.add(episode);
		episode.setPodcast(this);
		episodeGuids.add(episode.getGuid());
	}
	
	public void addAllEpisodes(List<Episode> episodes) {
		for (Episode episode : episodes) {
			addEpisode(episode);
		}
	}
	
	public void removeEpisode(Episode episode) {
		episodes.remove(episode);
		episode.setPodcast(null);
		episodeGuids.remove(episode.getGuid());
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@JsonIgnore
	public Set<String> getEpisodeGuids() {
		if (this.episodeGuids.isEmpty()) {
			for (Episode episode : this.getEpisodes()) {
				this.episodeGuids.add(episode.getGuid());
			}
		}
		return episodeGuids;
	}

	public void setEpisodeGuids(Set<String> episodeGuids) {
		this.episodeGuids = episodeGuids;
	}

}
