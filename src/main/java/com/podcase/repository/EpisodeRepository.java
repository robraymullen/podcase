package com.podcase.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.podcase.model.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
	
	Optional<Episode> findByTitle(String title);

	List<Episode> findAllByOrderByIdDesc();
	
	List<Episode> findByDownloaded(boolean downloaded);

	@Query(value="select * from episode where id = (select * from (SELECT episode_id from play_state where user_id = ?1 order by last_played desc limit 1) as subquery)", nativeQuery=true)
	Optional<Episode> getMostRecentlyPlayed();
}
