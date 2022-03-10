package com.podcase.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.podcase.serializer.PodcastSerializer;

@Entity
@NamedNativeQuery(
	    name = "find_subscribed_episodes",
	    query =
	    	"select episode.*, play_state.play_length from episode full outer join "
	    	+ "play_state on episode.podcast_id = :podcastId and episode.id = play_state.episode_id "
	    	+ "and play_state.user_id = :userId order by episode.id asc",
	    resultSetMapping = "subscribed_episodes"
	)
@SqlResultSetMapping(
		name = "subscribed_episodes",
	    classes = @ConstructorResult(
	        targetClass = com.podcase.model.SubscribedEpisode.class,
	        columns = {
	            @ColumnResult(name="id", type=BigInteger.class),
	            @ColumnResult(name="podcast_id", type=BigInteger.class),
	            @ColumnResult(name="title", type=String.class),
	            @ColumnResult(name="link", type=String.class),
	            @ColumnResult(name="description", type=String.class),
	            @ColumnResult(name="subtitle", type=String.class),
	            @ColumnResult(name="keywords", type=String.class),
	            @ColumnResult(name="summary", type=String.class),
	            @ColumnResult(name="creator", type=String.class),
	            @ColumnResult(name="image_url", type=String.class),
	            @ColumnResult(name="file_type", type=String.class),
	            @ColumnResult(name="file_length", type=BigInteger.class),
	            @ColumnResult(name="file_name", type=String.class),
	            @ColumnResult(name="file_path", type=String.class),
	            @ColumnResult(name="file_url", type=String.class),
	            @ColumnResult(name="downloaded", type=Boolean.class),
	            @ColumnResult(name="guid", type=String.class),
	            @ColumnResult(name="publication_date", type=Date.class),
	            @ColumnResult(name="retrieved_date", type=Date.class),
	            @ColumnResult(name="play_length", type=BigInteger.class)

	        }
	    )
	)

public class Episode implements Comparable<Episode> {

	@Id
	@GeneratedValue(generator = "episode_generator")
	@SequenceGenerator(name = "episode_generator", sequenceName = "episode_sequence", initialValue = 1)
	private Long id;
	
	@NotBlank
	@Field
	String title;
	
	@NotBlank
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	@Size(max = 4000)
	private String link;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "publication_date")
	private Date publicationDate;
	
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	@Size(max = 4000)
	private String description;
	
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	String subtitle;
	
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	String keywords;
	
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	String summary;
	
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	String creator;
	
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	String imageUrl;
	
	@NotBlank
	@Field(analyzer = @Analyzer(definition = "textanalyzer"))
	String fileUrl;
	
	@Field
	String fileType;
	
	@Field
	Long fileLength;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonSerialize(using = PodcastSerializer.class)
	private Podcast podcast;
	
	@NotNull
	@Field
	Date retrievedDate = new Date();
	
	@NotBlank
	@NotNull
	@Field
	String guid;

	@Size(max = 4000)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  
	private String fileName;
	
	private boolean downloaded = false;
	
	/**
	 * Generated URL for playing from podcase archive
	 */
	@Transient
	private String podcaseUrl;

	@JsonIgnore
	@Transient
	private String fileLocation;

	/*
	 * Path to the file from the root store: podcast.file.store
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  
	@Size(max = 10000)
	private String filePath;

	@Override
	public String toString() {
		return "Episode [id=" + id + ", title=" + title + ", link=" + link + ", publicationDate=" + publicationDate
				+ ", description=" + description + ", subtitle=" + subtitle + ", keywords=" + keywords + ", summary="
				+ summary + ", creator=" + creator + ", imageUrl=" + imageUrl + ", fileUrl=" + fileUrl + ", fileType="
				+ fileType + ", fileLength=" + fileLength + ", podcast=" + podcast + ", retrievedDate=" + retrievedDate
				+ ", guid=" + guid + ", fileName=" + fileName + ", fileLocation=" + fileLocation + ", filePath="
				+ filePath + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Podcast getPodcast() {
		return podcast;
	}

	public void setPodcast(Podcast podcast) {
		this.podcast = podcast;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getFileLength() {
		return fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	public Date getRetrievedDate() {
		return retrievedDate;
	}

	public void setRetrievedDate(Date retrievedDate) {
		this.retrievedDate = retrievedDate;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public boolean isDownloaded() {
		return downloaded;
	}

	public void setDownloaded(boolean downloaded) {
		this.downloaded = downloaded;
	}

	public String getPodcaseUrl() {
		return podcaseUrl;
	}

	public void setPodcaseUrl(String podcaseUrl) {
		this.podcaseUrl = podcaseUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Sort by newest -> oldest
	 */
	@Override
	public int compareTo(Episode o) {
		return o.getRetrievedDate().compareTo(this.getRetrievedDate());
	}
}
