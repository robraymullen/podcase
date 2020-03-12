package com.podcase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.podcase.model.PlayState;

public interface PlayStateRepository extends JpaRepository<PlayState, Long> {
	
	Optional<PlayState> findByUserIdAndEpisodeId(Long userId, Long episodeId);

}
