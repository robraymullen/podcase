package com.podcase.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.podcase.model.DeadDownload;
import com.podcase.projection.DeadDownloadProjection;

@Repository
public interface DeadDownloadRepository extends JpaRepository<DeadDownload, Long> {
	
	Optional<DeadDownload> findByEpisodeId(Long episodeId);
	
	@Query(value="SELECT dead_download.id, dead_download.attempt_count as attemptCount, "
			+ "dead_download.last_download_attempt as lastDownloadAttempt, dead_download.episode_id as episodeId, "
			+ "episode.file_url as fileUrl, episode.title as title from dead_download left "
			+ "outer join episode on dead_download.episode_id = episode.id", nativeQuery=true)
	List<DeadDownloadProjection> findDeadDownloadsWithEpisodeData();

}
