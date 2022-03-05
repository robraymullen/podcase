package com.podcase.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.podcase.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String expectedName);
	
//	Set<PodcastSubscription> findSubscriptionsById(Long userId);

}
