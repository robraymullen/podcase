package com.podcase.repository;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
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
import com.podcase.model.Podcast;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class PodcastRepositoryTest extends AbstractRepositoryTest {
	
	@Autowired
	PodcastRepository podcastRepository;
	
	Podcast podcast;
	
	@Before
	public void setUp() throws Exception {
		podcast = new Podcast();
		// setup non empty fields to prevent constraint violations
		podcast.setName("name");
		podcast.setLink("link");
		podcast.setRssFeed("blank");
		podcast.setLastBuildDate(new Date());
		podcast.setDescription("description");
		podcast.setAuthor("author");
	}

	@Rollback
	@After
	public void tearDown() throws Exception {
	}

	@Transactional
	@Test
	public void testGetSinglePodcastById() {
		persist(podcast);	
		// Tests run out of order so id cannot be predicted in advance.
		List<Podcast> podcasts = podcastRepository.findAll();
		Long id = new Long(podcasts.get(0).getId());
		Optional<Podcast> actualPodcast = podcastRepository.findById(id);
		assertEquals(id, actualPodcast.get().getId());
	}
	
	@Transactional
	@Test
	public void testGetSinglePodcastByName() {
		String expectedName = "Podcast Name";
		podcast.setName(expectedName);
		persist(podcast);	
        Optional<Podcast> actualPodcast = podcastRepository.findByName(expectedName);
		assertEquals(expectedName, actualPodcast.get().getName());
	}
	
	@Transactional
	@Test
	public void testGetPodcastLink() {
		String expectedLink = "http://www.lasertimepodcast.com";
		podcast.setLink(expectedLink);
		persist(podcast);	
		Optional<Podcast> actualPodcast = podcastRepository.findByLink(expectedLink);
		assertEquals(expectedLink, actualPodcast.get().getLink());
	}

	@Transactional
	@Test
	public void testGetPodcastRssFeed() {
		String expectedRssFeed = "http://www.lasertimepodcast.com/category/lasertimepodcast/feed/";
		podcast.setRssFeed(expectedRssFeed);
		persist(podcast);
        Optional<Podcast> actualPodcast = podcastRepository.findByRssFeed(expectedRssFeed);
        assertEquals(expectedRssFeed, actualPodcast.get().getRssFeed());
	}
	
	@Transactional
	@Test
	public void testGetLastBuildDate() {
		Date lastBuildDate = new Date();
		podcast.setLastBuildDate(lastBuildDate);
		persist(podcast);
		Optional<Podcast> actualPodcast = podcastRepository.findByLastBuildDate(lastBuildDate);
		assertEquals(lastBuildDate, actualPodcast.get().getLastBuildDate());
	}
	
	@Transactional
	@Test
	public void testGetDescription() {
		String description = "Expected podcast description";
		podcast.setDescription(description);
		persist(podcast);
		Optional<Podcast> actualPodcast = podcastRepository.findByDescription(description);
		assertEquals(description, actualPodcast.get().getDescription());
	}
	
	@Transactional
	@Test
	public void testAddEpisode() {
		Episode episode = new Episode();
		episode.setTitle("episode title");
		episode.setLink("link");
		episode.setFileUrl("fileUrl");
		episode.setDescription("description");
		episode.setPublicationDate(new Date());
		episode.setGuid("guid");
		podcast.addEpisode(episode);
		persist(podcast);
		
		Optional<Podcast> actualPodcast = podcastRepository.findByName("name");
		assertEquals(1, actualPodcast.get().getEpisodes().size());
	}
	
	@Transactional
	@Test
	public void testRemoveEpisode() {
		Episode episode = new Episode();
		episode.setTitle("episode title");
		episode.setLink("link");
		episode.setFileUrl("fileUrl");
		episode.setDescription("description");
		episode.setPublicationDate(new Date());
		episode.setGuid("guid");
		podcast.addEpisode(episode);
		persist(podcast);
		
		podcast.removeEpisode(episode);
		update(podcast);
		
		Optional<Podcast> actualPodcast = podcastRepository.findByName("name");
		assertEquals(0, actualPodcast.get().getEpisodes().size());
	}
	
	@Transactional
	@Test
	public void testEpisodeIsLinkedToPodcast() {
		Episode episode = new Episode();
		episode.setTitle("episode title");
		episode.setLink("link");
		episode.setFileUrl("fileUrl");
		episode.setDescription("description");
		episode.setPublicationDate(new Date());
		episode.setGuid("guid");
		podcast.addEpisode(episode);
		persist(podcast);
		
		Optional<Podcast> actualPodcast = podcastRepository.findByName("name");
		assertNotNull(actualPodcast.get().getEpisodes().get(0).getPodcast());
	}
	
	@Transactional
	@Test
	public void testGetAllRssFeeds() {
		podcast.setRssFeed("http://rss.feed");
		Episode episode = new Episode();
		episode.setTitle("episode title");
		episode.setLink("link");
		episode.setFileUrl("fileUrl");
		episode.setDescription("description");
		episode.setPublicationDate(new Date());
		episode.setGuid("guid");
		podcast.addEpisode(episode);
		persist(podcast);
		
		List<IRssFeed> rssFeeds = (List<IRssFeed>) podcastRepository.getRssFeeds();
		assertEquals(1, rssFeeds.size());
		assertTrue("http://rss.feed".equals(rssFeeds.get(0).getRss_Feed()));
	}
	
}
