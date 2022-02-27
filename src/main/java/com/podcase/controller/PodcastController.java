package com.podcase.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.podcase.factory.PodcastFactory;
import com.podcase.model.Podcast;
import com.podcase.repository.PodcastRepository;
import com.podcase.request.PodcastRequestBody;

@RestController
public class PodcastController {

	@Autowired
	PodcastRepository podcastRepository;
	
	@GetMapping("/podcasts")
	public List<Podcast> getAllPodcasts() {
		return podcastRepository.findAll();
	}
	
	@GetMapping("/podcasts/{id}")
	public Podcast getPodcastById(@PathVariable("id") Long id) {
		return podcastRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
	}
	
	@PutMapping("/podcasts")
	public Podcast addPodcast(@Valid @RequestBody PodcastRequestBody podcastRequest) {
		//Todo run a job to add the podcast to the db and also download all episodes
		Optional<Podcast> podcast = PodcastFactory.generate(podcastRequest.getPodcastUrl());
		return podcastRepository.save(podcast.orElseThrow());
	}
}
