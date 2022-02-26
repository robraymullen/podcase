package com.podcase.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.podcase.model.PlayState;
import com.podcase.repository.PlayStateRepository;

@Controller
public class PlayStateController {

	PlayStateRepository repository;
	
	@Autowired
	public PlayStateController(PlayStateRepository repository) {
		this.repository = repository;
	}
	
	@MessageMapping("/playstate")
	@SendTo("/topic/playstate")
	public PlayState updatePlayState(PlayState playStateMessage) throws Exception {
		Optional<PlayState> previousState = repository.findById(playStateMessage.getId());
		previousState.ifPresentOrElse((playState) -> {
			playState.setPlayLength(playStateMessage.getPlayLength());
			repository.save(playState);
		}, () -> {
			repository.save(playStateMessage);
		});
		return playStateMessage;
	}

}
