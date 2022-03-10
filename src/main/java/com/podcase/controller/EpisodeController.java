package com.podcase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.podcase.model.Episode;
import com.podcase.model.SubscribedEpisode;
import com.podcase.repository.EpisodeRepository;
import com.podcase.repository.SubscribedEpisodeRepository;

@RestController
public class EpisodeController {

	
	private EpisodeRepository episodeRepository;
	private SubscribedEpisodeRepository subscribedRepository;
	
	@Autowired
	public EpisodeController(EpisodeRepository episodeRepository, SubscribedEpisodeRepository subscribedRepository) {
		this.episodeRepository = episodeRepository;
		this.subscribedRepository = subscribedRepository;
	}
	
	@GetMapping("/episodes/{id}")
	public Episode getEpisodeById(@PathVariable("id") Long id) {
		return episodeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
	}
	
	@GetMapping("/episodes/playstate/{podcastId}/user/{userId}") 
	public List<SubscribedEpisode> getEpisodesWithPlayStateForUser(@PathVariable("podcastId") Long podcastId, @PathVariable("userId") Long userId) {
		return subscribedRepository.getEpisodesWithPlayState(podcastId, userId);
	}
	
	@GetMapping("/episodes/recent")
	public Episode getMostRecentUnfinishedEpisode() {
		return episodeRepository.getMostRecentlyPlayed().orElseThrow(ResourceNotFoundException::new);
	}
}
