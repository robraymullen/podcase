package com.podcase.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.podcase.dto.PodcastSubscription;
import com.podcase.model.Episode;
import com.podcase.model.SubscribedEpisode;
import com.podcase.repository.EpisodeRepository;
import com.podcase.repository.UserRepository;

@RestController
public class EpisodeController {

	
	private EpisodeRepository episodeRepository;
	private UserRepository userRepository;
	
	@Autowired
	public EpisodeController(EpisodeRepository episodeRepository, UserRepository userRepository) {
		this.episodeRepository = episodeRepository;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/episodes/{id}")
	public Episode getEpisodeById(@PathVariable("id") Long id) {
		return episodeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
	}
	
	@GetMapping("/episodes/playstate/{podcastId}/user/{userId}") 
	public List<SubscribedEpisode> getEpisodesWithPlayStateForUser(@PathVariable("podcastId") Long podcastId, @PathVariable("userId") Long userId) {
		Set<PodcastSubscription> podcasts = userRepository.findSubscriptionsById(userId);
		boolean isSubscribed = false;
		for (PodcastSubscription subscription : podcasts) {
			if (subscription.getId().equals(podcastId)) {
				isSubscribed = true;
				break;
			}
		}
		if (!isSubscribed) {
			throw new ResourceNotFoundException();
		}
		return episodeRepository.getEpisodesWithPlayState(podcastId, userId);
	}
	
	@GetMapping("/episodes/recent/user/{userId}") //TODO return SubscribedEpisode to include play state
	public SubscribedEpisode getMostRecentUnfinishedEpisode(@PathVariable("userId") Long userId) {
		return episodeRepository.getMostRecentlyPlayed(userId).orElseThrow(ResourceNotFoundException::new);
	}
}
