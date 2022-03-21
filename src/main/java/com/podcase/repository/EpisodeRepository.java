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

	@Query(value="select * from episode where id = (select * from (SELECT episode_id from play_state where user_id = ?1 order by last_played desc limit 1) as subquery)", nativeQuery=true)
	Optional<Episode> getMostRecentlyPlayed(@Param("userId") Long userId);
	
	@Query(name="find_subscribed_episodes", nativeQuery=true)
	List<SubscribedEpisode> getEpisodesWithPlayState(@Param("podcastId") Long podcastId, @Param("userId")  Long userId);
}
