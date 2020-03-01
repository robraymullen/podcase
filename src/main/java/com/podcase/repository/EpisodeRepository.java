package com.podcase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.podcase.model.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
	
	Optional<Episode> findByTitle(String title);

}
