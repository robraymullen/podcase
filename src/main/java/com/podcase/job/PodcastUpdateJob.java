package com.podcase.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.podcase.factory.PodcastFactory;
import com.podcase.model.Episode;
import com.podcase.model.Podcast;
import com.podcase.repository.EpisodeRepository;
import com.podcase.repository.PodcastRepository;

@Service
@Qualifier("podcastUpdateJob")
public class PodcastUpdateJob implements ScheduledJob {

	PodcastRepository podcastRepository;
	
	EpisodeRepository episodeRepository;
	
	@Autowired
	public PodcastUpdateJob(PodcastRepository podcastRepository, EpisodeRepository episodeRepository) {
		this.podcastRepository = podcastRepository;
		this.episodeRepository = episodeRepository;
	}
	
	@Override
	public void process() {
		List<Podcast> podcasts = podcastRepository.findAll();
		
		for (Podcast podcast : podcasts) {
			List<Episode> episodes = PodcastFactory.getNewEpisodes(podcast);
			for (Episode episode : episodes) {
				podcast.addEpisode(episode);
			}
			podcastRepository.save(podcast);
		}
	}
}
