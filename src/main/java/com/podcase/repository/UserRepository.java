package com.podcase.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.podcase.dto.PodcastSubscription;
import com.podcase.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String expectedName);
	
	@Query(value="select id, name, description, image_url as imageUrl from podcast where id in (select * from (SELECT subscriptions_id from users_subscriptions where user_id = ?1) as subquery)", nativeQuery = true)
	Set<PodcastSubscription> findSubscriptionsById(Long id);

}
