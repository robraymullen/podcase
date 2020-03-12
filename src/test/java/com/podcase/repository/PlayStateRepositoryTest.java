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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.podcase.model.Episode;
import com.podcase.model.User;
import com.podcase.model.PlayState;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class PlayStateRepositoryTest extends AbstractRepositoryTest {
	
	@Autowired
	PlayStateRepository watchStateRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EpisodeRepository episodeRepository;
	
	PlayState playState;
	
	User user;
	
	Episode episode;


	@Before
	public void setUp() throws Exception {
		playState = new PlayState();
		user = new User();
		user.setName("Name");
		user.setPassword("password");
		
		episode = new Episode();
		episode.setTitle("title");
		episode.setLink("link");
		episode.setFileUrl("fileUrl");
		episode.setDescription("description");
		episode.setPublicationDate(new Date());
		episode.setGuid("guid");
	}

	@Rollback
	@After
	public void tearDown() throws Exception {
	}

	@Transactional
	@Test
	public void testGetWatchStateByUserIdAndEpisodeId() {
		persist(user);
        
        persist(episode);
        
		Long watchLength = new Long(1234);
		playState.setUser(user);
		playState.setEpisode(episode);
		playState.setPlayLength(watchLength);
		persist(playState);
		
		Optional<User> actualUser = userRepository.findByName("Name");
		Optional<Episode> actualEpisode = episodeRepository.findByTitle("title");
		
		Optional<PlayState> actualWatchState = watchStateRepository.findByUserIdAndEpisodeId(actualUser.get().getId(), actualEpisode.get().getId());
		assertEquals(watchLength, actualWatchState.get().getPlayLength());
	}

}
