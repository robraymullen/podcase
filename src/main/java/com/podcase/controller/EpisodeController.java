package com.podcase.controller;

import java.util.List;
import java.util.Optional;
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
	
	@GetMapping("/episodes/{episodeId}/next/user/{userId}")
	public SubscribedEpisode getNextEpisode(@PathVariable("episodeId") Long episodeId, @PathVariable("userId") Long userId) {
		Optional<Episode> initialEpisode = episodeRepository.findById(episodeId);
		if (initialEpisode.isEmpty()) {
			throw new ResourceNotFoundException("No episode found for id: "+episodeId);
		}
		Optional<SubscribedEpisode> nextEpisode = episodeRepository.getNextEpisodeForPodcast(initialEpisode.get().getPodcast().getId(), initialEpisode.get().getPublicationDate(), userId);
		if (nextEpisode.isEmpty()) {
			throw new ResourceNotFoundException("No next episode found for Podcast: "+initialEpisode.get().getPodcast().getName());
		}
		return nextEpisode.get();
	}
}
