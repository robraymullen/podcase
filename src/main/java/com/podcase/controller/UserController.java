package com.podcase.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.podcase.model.Podcast;
import com.podcase.model.User;
import com.podcase.repository.PodcastRepository;
import com.podcase.repository.UserRepository;
import com.podcase.request.UserRequestBody;
import com.podcase.serializer.PodcastSerializer;

@RestController
public class UserController {
	
	private UserRepository userRepository;
	private PodcastRepository podcastRepository;

	@Autowired
	public UserController(UserRepository userRepository, PodcastRepository podcastRepository) {
		this.userRepository = userRepository;
		this.podcastRepository = podcastRepository;
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
	}
	
	@GetMapping("/users/{userId}/subscriptions")
	public Set<Podcast> getUserSubscriptions(@PathVariable("userId") Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		return user.getSubscriptions();
	}
	
	@PostMapping("/users/add")
	public User addUser(@Valid @RequestBody UserRequestBody user) {
		User newUser = new User();
		newUser.setName(user.getName());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		newUser.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(newUser);
	}
	
	@PutMapping("/users/subscriptions")
	public User addSubscription(@Valid @RequestBody UserRequestBody user) {
		User selectedUser = this.userRepository.findByName(user.getName()).orElseThrow();
		Podcast podcast = this.podcastRepository.findByName(user.getSubscriptionName()).orElseThrow();
		selectedUser.addSubscription(podcast);
		return userRepository.save(selectedUser);
	}

}
