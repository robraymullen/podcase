package com.podcase.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.podcase.model.Episode;
import com.podcase.model.SubscribedEpisode;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
	
	Optional<Episode> findByTitle(String title);

	List<Episode> findAllByOrderByIdDesc();
	
	List<Episode> findByDownloaded(boolean downloaded);

	@Query(name="find_most_recent_played", nativeQuery=true)
	Optional<SubscribedEpisode> getMostRecentlyPlayed(@Param("userId") Long userId);
	
	@Query(name="find_subscribed_episodes", nativeQuery=true)
	List<SubscribedEpisode> getEpisodesWithPlayState(@Param("podcastId") Long podcastId, @Param("userId")  Long userId);
}
