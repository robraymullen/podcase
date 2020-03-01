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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.podcase.model.Episode;
import com.podcase.model.Podcast;
import com.podcase.model.User;
import com.podcase.model.WatchState;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class FullRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	WatchStateRepository watchStateRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EpisodeRepository episodeRepository;
	
	@Autowired
	PodcastRepository podcastRepository;
	
	WatchState watchState;
	
	User user;
	
	Episode episode;

	Podcast podcast;

	@Before
	public void setUp() throws Exception {
		user = new User();
		user.setName("Name");
		user.setPassword("password");
		
		episode = new Episode();
		episode.setTitle("episode title");
		episode.setLink("link");
		episode.setFileUrl("fileUrl");
		episode.setDescription("description");
		episode.setPublicationDate(new Date());
		
		podcast = new Podcast();
		podcast.setName("podcast name");
		podcast.setLink("link");
		podcast.setRssFeed("blank");
		podcast.setLastBuildDate(new Date());
		podcast.setDescription("description");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSubscribingAUserToASinglePodcast() {
		podcast.addEpisode(episode);
		user.addSubscription(podcast);
		persist(user);
		
		Optional<User> actualUser = userRepository.findByName("Name");
		Optional<Podcast> actualPodcast = podcastRepository.findByName(actualUser.get().getSubscriptions().get(0).getName());
		System.out.println("Episode title: " + actualPodcast.get().getEpisodes().get(0).getTitle());
		Optional<Episode> actualEpisode = episodeRepository.findByTitle(actualPodcast.get().getEpisodes().get(0).getTitle());
		assertEquals("podcast name", actualPodcast.get().getName());
		assertEquals("episode title", actualEpisode.get().getTitle());
	}
	
	@Test
	public void testSubscribingAUserToMultiplePodcasts() {
		Podcast pod2 = new Podcast();
		pod2 = new Podcast();
		pod2.setName("podcast name 2");
		pod2.setLink("link2");
		pod2.setRssFeed("blank2");
		pod2.setLastBuildDate(new Date());
		pod2.setDescription("description2");
		
		user.addSubscription(podcast);
		user.addSubscription(pod2);
		persist(user);
		
		Optional<User> actualUser = userRepository.findByName("Name");
		assertEquals(2, actualUser.get().getSubscriptions().size());
		
		Optional<Podcast> podcastOne = podcastRepository.findByName(actualUser.get().getSubscriptions().get(0).getName());
		Optional<Podcast> podcastTwo = podcastRepository.findByName(actualUser.get().getSubscriptions().get(1).getName());
		assertEquals("podcast name", podcastOne.get().getName());
		assertEquals("podcast name 2", podcastTwo.get().getName());
	}
	
	@Test
	public void testSettingTheWatchStateForAnEpisodeThatAUserIsSubscribedTo() {
		podcast.addEpisode(episode);
		user.addSubscription(podcast);
		persist(user);
		
		Optional<User> actualUser = userRepository.findByName("Name");
		Episode actualEpisode = actualUser.get().getSubscriptions().get(0).getEpisodes().get(0);
		
		watchState = new WatchState();
		watchState.setWatchedLength(new Long(1234));
		watchState.setEpisode(actualEpisode);
		watchState.setUser(user);
		persist(watchState);
		
		Optional<WatchState> actualWatchState = watchStateRepository.findByUserIdAndEpisodeId(actualUser.get().getId(), actualEpisode.getId());
		assertEquals(new Long(1234), actualWatchState.get().getWatchedLength());
	}
	
	@Test
	public void testUpdateWatchStateForAnEpisodeThatAUserIsSubscribedTo() {
		podcast.addEpisode(episode);
		user.addSubscription(podcast);
		persist(user);
		
		Optional<User> actualUser = userRepository.findByName("Name");
		Episode actualEpisode = actualUser.get().getSubscriptions().get(0).getEpisodes().get(0);
		
		watchState = new WatchState();
		watchState.setWatchedLength(new Long(1234));
		watchState.setEpisode(actualEpisode);
		watchState.setUser(user);
		persist(watchState);
		
		Optional<WatchState> actualWatchState = watchStateRepository.findByUserIdAndEpisodeId(actualUser.get().getId(), actualEpisode.getId());
		WatchState watchStateOne = actualWatchState.get();
		
		watchStateOne.setWatchedLength(new Long(12345));
		update(watchStateOne);
		
		Optional<WatchState> actualUpdatedWatchState = watchStateRepository.findByUserIdAndEpisodeId(actualUser.get().getId(), actualEpisode.getId());
		assertEquals(new Long(12345), actualUpdatedWatchState.get().getWatchedLength());
	}
	
	@Test
	public void testNoDuplicatesAfterUpdateOfWatchState() {
		podcast.addEpisode(episode);
		user.addSubscription(podcast);
		persist(user);
		
		Optional<User> actualUser = userRepository.findByName("Name");
		Episode actualEpisode = actualUser.get().getSubscriptions().get(0).getEpisodes().get(0);
		
		watchState = new WatchState();
		watchState.setWatchedLength(new Long(1234));
		watchState.setEpisode(actualEpisode);
		watchState.setUser(user);
		persist(watchState);
		
		Optional<WatchState> actualWatchState = watchStateRepository.findByUserIdAndEpisodeId(actualUser.get().getId(), actualEpisode.getId());
		WatchState watchStateOne = actualWatchState.get();
		
		watchStateOne.setWatchedLength(new Long(12345));
		update(watchStateOne);
		
		List<User> users = userRepository.findAll();
		assertEquals(1, users.size());
		
		List<Podcast> podcasts = podcastRepository.findAll();
		assertEquals(1, podcasts.size());
		
		List<Episode> episodes = episodeRepository.findAll();
		assertEquals(1, episodes.size());
		
		List<WatchState> watchStates = watchStateRepository.findAll();
		assertEquals(1, watchStates.size());
	}

}
