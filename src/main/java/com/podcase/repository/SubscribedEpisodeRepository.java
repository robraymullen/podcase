package com.podcase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.podcase.model.SubscribedEpisode;

@Repository
public interface SubscribedEpisodeRepository extends JpaRepository<SubscribedEpisode, Long> {
	
	@Query(name="find_subscribed_episodes", nativeQuery=true)
	List<SubscribedEpisode> getEpisodesWithPlayState(@Param("podcastId") Long podcastId, @Param("userId")  Long userId);

}
