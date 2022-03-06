package com.podcase.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.podcase.dto.PodcastSubscription;
import com.podcase.model.Podcast;
import com.podcase.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class UserRepositoryTest extends AbstractRepositoryTest {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PodcastRepository podcastRepository;
	
	User user;

	@Before
	public void setUp() throws Exception {
		user = new User();
		user.setName("name");
		user.setPassword("password");
	}

	@Rollback
	@After
	public void tearDown() throws Exception {
	}

	@Transactional
	@Test
	public void testGetSingleUserByName() {
		String name = "rob";
		user.setName(name);
		persist(user);
		Optional<User> actualUser = userRepository.findByName(name);
		assertEquals(name, actualUser.get().getName());
	}
	
	@Transactional
	@Test
	public void testGetUserSubscriptions() {
		String name = "rob";
		user.setName(name);
		persist(user);
		
		Podcast podcast = new Podcast();
		// setup non empty fields to prevent constraint violations
		podcast.setName("name");
		podcast.setLink("link");
		podcast.setRssFeed("blank");
		podcast.setLastBuildDate(new Date());
		podcast.setDescription("description");
		podcast.setAuthor("author");
		podcast.setImageUrl("imageUrl");
		
		persist(podcast);
		Podcast pod = podcastRepository.findAll().get(0);
		
		user.addSubscription(pod);
		update(user);
		
		User queriedUser = userRepository.findAll().get(0);
		Set<PodcastSubscription> subscriptions = userRepository.findSubscriptionsById(queriedUser.getId());
		assertEquals(1, subscriptions.size());
		
		PodcastSubscription sub = subscriptions.iterator().next();
		assertEquals("name", sub.getName());
		assertEquals("description", sub.getDescription());
		assertEquals("imageUrl", sub.getImageUrl());
	}
	
	@Transactional
	@Test(expected = PersistenceException.class)
	public void testNoDuplicateNamesPossible() {
		String name = "rob";
		user.setName(name);
		persist(user);
		
		User secondUser = new User();
		secondUser.setName(name);
		secondUser.setPassword("password");
		persist(secondUser);
	}

}
