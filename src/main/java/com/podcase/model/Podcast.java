package com.podcase.model;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;

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

	@NotBlank
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	@Size(max = 4000)
	private String link;

	@NotBlank
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	@Size(max = 4000)
	private String rssFeed;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_build_date")
	private Date lastBuildDate;

	@NotBlank
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	@Size(max = 4000)
	private String description;
	
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
		return "Podcast [id=" + id + ", name=" + name + ", link=" + link + "]";
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
}
