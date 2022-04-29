package com.podcase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.podcase.model.DeadDownload;

@Repository
public interface DeadDownloadRepository extends JpaRepository<DeadDownload, Long> {
	
	Optional<DeadDownload> findByEpisodeId(Long episodeId);

}
