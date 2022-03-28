package com.podcase.projection;

import java.util.Date;

public interface PodcastProjection {
	//id, author, description, image_url, last_build_date, link, name, rss_feed from podcast where id= ?1", nativeQuery=true)
	Long getId();
	String getAuthor();
	String getDescription();
	String getImageUrl();
	Date getLastBuildDate();
	String getLink();
	String getName();
	String getRssFeed();
	
}
