package com.podcase.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.podcase.dto.PodcastSubscription;
import com.podcase.model.Podcast;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, Long> {

	Optional<Podcast> findByName(String expectedName);

	Optional<Podcast> findByLink(String expectedLink);

	Optional<Podcast> findByRssFeed(String expectedRssFeed);

	Optional<Podcast> findByLastBuildDate(Date lastBuildDate);

	Optional<Podcast> findByDescription(String description);

	public static final String GET_PODCAST_RSS_FEEDS = "SELECT id, rss_feed FROM podcast";

	@Query(value = GET_PODCAST_RSS_FEEDS, nativeQuery = true)
	public List<IRssFeed> getRssFeeds();

	public List<Podcast> findAllByOrderById();
}
