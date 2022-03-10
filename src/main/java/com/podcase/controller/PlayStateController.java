package com.podcase.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.podcase.model.Episode;
import com.podcase.model.PlayState;
import com.podcase.repository.EpisodeRepository;
import com.podcase.repository.PlayStateRepository;
import com.podcase.repository.UserRepository;
import com.podcase.request.PlayStateRequest;

@Controller
public class PlayStateController {

	private PlayStateRepository repository;
	private EpisodeRepository episodeRepository;
	private UserRepository userRepository;
	PlayState currentPlayState;
	
	@Autowired
	public PlayStateController(PlayStateRepository repository, EpisodeRepository episodeRepository, UserRepository userRepository) {
		this.repository = repository;
		this.episodeRepository = episodeRepository;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/playstate/{episodeId}/user/{userId}")
	public PlayState getPlayState(@PathVariable("episodeId") Long episodeId, @PathVariable("userId") Long userId) {
		return repository.findByUserIdAndEpisodeId(userId, episodeId).orElseThrow();
	}
	
	@PostMapping("/playstate/")
	public ResponseEntity<String> postPlayState(@RequestBody PlayStateRequest playState) throws Exception {
		updatePlayState(playState);
		return new ResponseEntity<String>("PlayState added successfully. ", HttpStatus.OK);
	}
	
	
	@MessageMapping("/playstate")
	@SendTo("/topic/playstate")
	public PlayState processUpdateMessage(PlayStateRequest playStateMessage) throws Exception {
		return updatePlayState(playStateMessage);
	}
	
	private PlayState updatePlayState(PlayStateRequest playStateRequest) throws Exception {
		repository.findById(playStateRequest.getPlayStateId() == null ? -1 : playStateRequest.getPlayStateId()).ifPresentOrElse((playState) -> {
			playState.setPlayLength(playStateRequest.getPlayLength());
			this.currentPlayState = repository.save(playState);
		}, () -> {
			PlayState playState = new PlayState();
			playState.setEpisode(episodeRepository.findById(playStateRequest.getEpisodeId()).orElseThrow());
			playState.setLastPlayed(new Date(playStateRequest.getLastPlayed()));
			playState.setPlayed(playStateRequest.isPlayed());
			playState.setUser(userRepository.findById(playStateRequest.getUserId()).orElseThrow());
			playState.setPlayLength(playStateRequest.getPlayLength());
			this.currentPlayState = repository.save(playState);
		});
		return this.currentPlayState;
	}

}
