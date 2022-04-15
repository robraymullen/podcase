package com.podcase.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.podcase.factory.PodcastFactory;
import com.podcase.model.Podcast;
import com.podcase.model.User;
import com.podcase.projection.PodcastProjection;
import com.podcase.repository.PodcastRepository;
import com.podcase.repository.UserRepository;
import com.podcase.request.PodcastRequestBody;
import com.podcase.request.PodcastSubscriptionRequest;

@RestController
public class PodcastController {

	PodcastRepository podcastRepository;
	
	UserRepository userRepository;
	
	@Autowired
	public PodcastController(PodcastRepository podcastRepository, UserRepository userRepository) {
		this.podcastRepository = podcastRepository;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/podcasts")
	public List<Podcast> getAllPodcasts() {
		return podcastRepository.findAll();
	}
	
	@GetMapping("/podcasts/{id}")
	public PodcastProjection getPodcastById(@PathVariable("id") Long id) {
		return podcastRepository.getPodcastMetadataById(id).orElseThrow(ResourceNotFoundException::new);
	}
	
	@PostMapping("/podcasts")
	public ResponseEntity<String> addPodcast(@Valid @RequestBody PodcastRequestBody podcastRequest) {
		Optional<Podcast> podcast = PodcastFactory.generate(podcastRequest.getPodcastUrl());
		podcastRepository.save(podcast.orElseThrow());
		return new ResponseEntity<String>("Podcast added successfully. ", HttpStatus.OK);
	}
	
	@PostMapping("/podcasts/subscription")
	public ResponseEntity<String> addSubscriptionPodcast(@Valid @RequestBody PodcastSubscriptionRequest podcastRequest) {
		Optional<Podcast> optPodcast = podcastRepository.findByName(podcastRequest.getPodcastName());
		Podcast podcast;
		if (optPodcast.isPresent()) {
			podcast = optPodcast.get();
		} else {
			Optional<Podcast> optGenPodcast = PodcastFactory.generate(podcastRequest.getPodcastUrl());
			podcastRepository.save(optGenPodcast.orElseThrow());
			podcast = optGenPodcast.get();
		}
		User selectedUser = this.userRepository.findByName(podcastRequest.getUserName()).orElseThrow();
		selectedUser.addSubscription(podcast);
		userRepository.save(selectedUser);
		
		return new ResponseEntity<String>("User subscription added successfully. ", HttpStatus.OK);
	}
}
