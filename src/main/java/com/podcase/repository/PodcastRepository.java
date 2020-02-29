package com.podcase.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.podcase.model.Podcast;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, Long> {

	Optional<Podcast> findByName(String expectedName);

	Optional<Podcast> findByLink(String expectedLink);

	Optional<Podcast> findByRssFeed(String expectedRssFeed);

	Optional<Podcast> findByLastBuildDate(Date lastBuildDate);

	Optional<Podcast> findByDescription(String description);

}
