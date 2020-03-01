package com.podcase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.podcase.model.WatchState;

public interface WatchStateRepository extends JpaRepository<WatchState, Long> {
	
	Optional<WatchState> findByUserIdAndEpisodeId(Long userId, Long episodeId);

}
