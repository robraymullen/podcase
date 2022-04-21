package com.podcase.repository;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.podcase.model.Episode;
import com.podcase.model.PlayState;
import com.podcase.model.Podcast;
import com.podcase.model.SubscribedEpisode;
import com.podcase.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class EpisodeRepositoryTest extends AbstractRepositoryTest {
	
	@Autowired
	EpisodeRepository episodeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PodcastRepository podcastRepository;
	
	@Autowired
	PlayStateRepository playStateRepository;
	
	Episode episode;
	Podcast podcast;
	User user;

	@Before
	public void setUp() throws Exception {
		episode = new Episode();
		episode.setTitle("episode title");
		episode.setLink("link");
		episode.setFileUrl("fileUrl");
		episode.setDescription("description");
		episode.setPublicationDate(new Date());
		episode.setGuid("guid");
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Transactional
	@Rollback
	@Test
	public void testGetSingleEpisodeById() {
		persist(episode);
		List<Episode> episodes = episodeRepository.findAll();
		Long id = Long.valueOf(episodes.get(0).getId());
		Optional<Episode> actualEpisode = episodeRepository.findById(id);
		assertEquals(id, actualEpisode.get().getId());
	}
	
	@Transactional
	@Rollback
	@Test
	public void testFindingAllEpisodesThatAreNotDownloaded() {
		persist(episode);
		Episode ep2 = new Episode();
		ep2.setTitle("ep2 title");
		ep2.setLink("link");
		ep2.setFileUrl("fileUrl");
		ep2.setDescription("description");
		ep2.setPublicationDate(new Date());
		ep2.setGuid("guid2");
		persist(ep2);
		
		List<Episode> episodes = episodeRepository.findByDownloaded(false);
		assertEquals(2, episodes.size());
	}
	
	@Transactional
	@Rollback
	@Test
	public void testGetMostRecentlyPlayed() {
		setupUserSubscriptions();
		setupPlayState();
		Optional<SubscribedEpisode> subscribedEpisode = episodeRepository.getMostRecentlyPlayed(userRepository.findAll().get(0).getId());
		assertTrue(subscribedEpisode.isPresent());
		assertEquals("ep2 title", subscribedEpisode.get().getTitle());
	}
	
	@Transactional
	@Rollback
	@Test
	public void testGetEpisodesWithPlayState() {
		persist(episode);
		setupUserSubscriptions();
		setupPlayState();
		List<SubscribedEpisode> subscribedEpisodes = episodeRepository.getEpisodesWithPlayState(podcast.getId(), user.getId());
		assertEquals(2, subscribedEpisodes.size());
		assertEquals("episode title", subscribedEpisodes.get(0).getTitle());
		assertEquals("ep2 title", subscribedEpisodes.get(1).getTitle());
	}
	
	@Transactional
	@Rollback
	@Test
	public void testGetNextEpisodeForPodcast() {
		persist(episode);
		setupUserSubscriptions();
		setupPlayState();
		Optional<SubscribedEpisode> nextEpisode = episodeRepository.getNextEpisodeForPodcast(podcast.getId(), episode.getPublicationDate(), user.getId());
		assertTrue(nextEpisode.isPresent());
		assertEquals("ep2 title", nextEpisode.get().getTitle());
	}
	
	@Transactional
	@Rollback
	@Test
	public void testGetNextEpisodeWhenNoEpisodesLeft() {
		persist(episode);
		podcast = new Podcast();
		podcast.setLink("link");
		podcast.setName("podcast name");
		podcast.setDescription("podcast description");
		podcast.addEpisode(episode);
		podcast = podcastRepository.save(podcast);
		
		user = new User();
		user.setName("name");
		user.setPassword("password");
		user.addSubscription(podcast);
		user = userRepository.save(user);
		
		Optional<SubscribedEpisode> nextEpisode = episodeRepository.getNextEpisodeForPodcast(podcast.getId(), episode.getPublicationDate(), user.getId());
		assertTrue(nextEpisode.isEmpty());
	}
	
	private void setupUserSubscriptions() {
		podcast = new Podcast();
		podcast.setLink("link");
		podcast.setName("podcast name");
		podcast.setDescription("podcast description");
		podcast.addEpisode(episode);
		Episode ep2 = new Episode();
		ep2.setTitle("ep2 title");
		ep2.setLink("link");
		ep2.setFileUrl("fileUrl");
		ep2.setDescription("description");
		ep2.setPublicationDate(new Date());
		ep2.setGuid("guid2");
		podcast.addEpisode(ep2);
		podcast = podcastRepository.save(podcast);
		
		user = new User();
		user.setName("name");
		user.setPassword("password");
		user.addSubscription(podcast);
		user = userRepository.save(user);
	}
	
	private void setupPlayState() {
		PlayState playState = new PlayState();
		playState.setEpisode(episodeRepository.findByTitle("episode title").orElseThrow());
		playState.setPlayLength(Long.valueOf(1234));
		playState.setLastPlayed(new Date());
		playState.setUser(user);
		playStateRepository.save(playState);
		
		PlayState playState2 = new PlayState();
		playState2.setEpisode(episodeRepository.findByTitle("ep2 title").orElseThrow());
		playState2.setPlayLength(Long.valueOf(12345));
		playState2.setLastPlayed(new Date());
		playState2.setUser(user);
		playStateRepository.save(playState2);
	}

}
