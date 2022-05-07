package com.podcase.model;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.ColumnResult;

/*
 * This is truly awful. There must be another way to generate a field value
 * dynamically from a join or calculation. Will need to do more research on this.
 * Having to essentially just match an existing class just so that you can map a result to
 * an entity is irritating
 */
public class SubscribedEpisode {
	
	private BigInteger play_state_id;
	private Integer duration;
	private Integer play_length;
	private Date retrieved_date;
	private Date publication_date;
	private String guid;
	private Boolean downloaded;
	private String file_url;
	private String file_path;
	private String file_name;
	private BigInteger file_length;
	private String file_type;
	private String image_url;
	private String creator;
	private String summary;
	private String keywords;
	private String subtitle;
	private String description;
	private String link;
	private String title;
	private BigInteger podcast_id;
	@Id
	private BigInteger id;
	
	public SubscribedEpisode() {
		
	}

	public SubscribedEpisode(BigInteger id, 
			BigInteger podcast_id,
			String title,
			String link,
			String description,
			String subtitle,
			String keywords,
			String summary,
			String creator,
			String image_url,
			String file_type,
			BigInteger file_length,
			String file_name,
			String file_path,
			String file_url,
			Boolean downloaded,
			String guid,
			Date publication_date,
			Date retrieved_date,
			Integer play_length,
			Integer duration,
			BigInteger play_state_id) {
				this.id = id;
				this.podcast_id = podcast_id;
				this.title = title;
				this.link = link;
				this.description = description;
				this.subtitle = subtitle;
				this.keywords = keywords;
				this.summary = summary;
				this.creator = creator;
				this.image_url = image_url;
				this.file_type = file_type;
				this.file_length = file_length;
				this.file_name = file_name;
				this.file_path = file_path;
				this.file_url = file_url;
				this.downloaded = downloaded;
				this.guid = guid;
				this.publication_date = publication_date;
				this.retrieved_date = retrieved_date;
				this.play_length = play_length;
				this.duration = duration;
				this.play_state_id = play_state_id;
		
	}
	
	public BigInteger getPlay_state_id() {
		return play_state_id;
	}
	
	public void setPlay_state_id(BigInteger play_state_id) {
		this.play_state_id = play_state_id;
	}

	public Integer getPlay_length() {
		return play_length;
	}

	public void setPlay_length(Integer play_length) {
		this.play_length = play_length;
	}

	public Date getRetrieved_date() {
		return retrieved_date;
	}

	public void setRetrieved_date(Timestamp retrieved_date) {
		this.retrieved_date = retrieved_date;
	}

	public Date getPublication_date() {
		return publication_date;
	}

	public void setPublication_date(Timestamp publication_date) {
		this.publication_date = publication_date;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Boolean getDownloaded() {
		return downloaded;
	}

	public void setDownloaded(Boolean downloaded) {
		this.downloaded = downloaded;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public BigInteger getFile_length() {
		return file_length;
	}

	public void setFile_length(BigInteger file_length) {
		this.file_length = file_length;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigInteger getPodcast_id() {
		return podcast_id;
	}

	public void setPodcast_id(BigInteger podcast_id) {
		this.podcast_id = podcast_id;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}
