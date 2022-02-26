package com.podcase.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.podcase.model.PlayState;

@Controller
public class PlayStateController {

	@MessageMapping("/playstate")
	@SendTo("/topic/playstate")
	public PlayState greeting(PlayState playStateMessage) throws Exception {
		return null;
	}

}
