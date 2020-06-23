package com.podcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.podcase.model.Episode;
import com.podcase.repository.EpisodeRepository;

@RestController
public class EpisodeController {

	@Autowired
	EpisodeRepository episodeRepository;
	
	@GetMapping("/episodes/{id}")
	public Episode getEpisodeById(@PathVariable("id") Long id) {
		return episodeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
	}
}
