package com.podcase.repository;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Entity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.podcase.model.Episode;
import com.podcase.model.User;
import com.podcase.model.WatchState;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class WatchStateRepositoryTest extends AbstractRepositoryTest {
	
	@Autowired
	WatchStateRepository watchStateRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EpisodeRepository episodeRepository;
	
	WatchState watchState;
	
	User user;
	
	Episode episode;


	@Before
	public void setUp() throws Exception {
		watchState = new WatchState();
		user = new User();
		user.setName("Name");
		user.setPassword("password");
		
		episode = new Episode();
		episode.setTitle("title");
		episode.setLink("link");
		episode.setFileUrl("fileUrl");
		episode.setDescription("description");
		episode.setPublicationDate(new Date());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetWatchStateByUserIdAndEpisodeId() {
		persist(user);
        
        persist(episode);
        
		Long watchLength = new Long(1234);
		watchState.setUser(user);
		watchState.setEpisode(episode);
		watchState.setWatchedLength(watchLength);
		persist(watchState);
		
		Optional<User> actualUser = userRepository.findByName("Name");
		Optional<Episode> actualEpisode = episodeRepository.findByTitle("title");
		
		Optional<WatchState> actualWatchState = watchStateRepository.findByUserIdAndEpisodeId(actualUser.get().getId(), actualEpisode.get().getId());
		assertEquals(watchLength, actualWatchState.get().getWatchedLength());
	}

}
