package com.podcase.repository;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.podcase.factory.PodcastFactory;
import com.podcase.model.Episode;
import com.podcase.model.Podcast;
import com.podcase.model.User;
import com.podcase.model.PlayState;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class RepositoryGenerationTest extends AbstractRepositoryTest {
	
	@Autowired
	PlayStateRepository watchStateRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EpisodeRepository episodeRepository;
	
	@Autowired
	PodcastRepository podcastRepository;
	
	PlayState playState;
	
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
		episode.setGuid("guid");
		
		podcast = new Podcast();
		podcast.setName("podcast name");
		podcast.setLink("link");
		podcast.setRssFeed("blank");
		podcast.setLastBuildDate(new Date());
		podcast.setDescription("description");
		podcast.setAuthor("author");
	}
	
	@Rollback
	@After
	public void tearDown() {
		
	}
	
	@Transactional
	@Test
	public void testRepositoryEpisodeCountFromNonItunes() throws MalformedURLException {
		File file = new File(getClass().getClassLoader().getResource("lasertime.xml").getFile());
		podcast = PodcastFactory.generate(file.toURI().toURL().toString()).get();
		persist(podcast);
		
		List<Episode> episodes = episodeRepository.findAll();
		assertEquals(411, episodes.size());
	}
	
	@Transactional
	@Test
	public void testRepositoryEpisodeCountFromItunes() throws MalformedURLException {
		File file = new File(getClass().getClassLoader().getResource("sincast.xml").getFile());
		podcast = PodcastFactory.generate(file.toURI().toURL().toString()).get();
		persist(podcast);
		
		List<Episode> episodes = episodeRepository.findAll();
		assertEquals(282, episodes.size());
	}
	
	@Transactional
	@Test
	public void testRepositoryEpisodePropertiesFromNonItunes() throws MalformedURLException {
		File file = new File(getClass().getClassLoader().getResource("lasertime.xml").getFile());
		podcast = PodcastFactory.generate(file.toURI().toURL().toString()).get();
		persist(podcast);
		
		List<Episode> episodes = episodeRepository.findAll();
		for (Episode episode : episodes) {
			assertEquals("http://www.lasertimepodcast.com", episode.getPodcast().getLink());
			assertNotNull(episode.getTitle());
			assertNotNull(episode.getLink());
			assertNotNull(episode.getFileUrl());
			assertNotNull(episode.getFileType());
			assertNotNull(episode.getFileLength());
			assertNotNull(episode.getPublicationDate());
		}
	}
	
	@Transactional
	@Test
	public void testRepositoryEpisodePropertiesFromItunes() throws MalformedURLException {
		podcast = PodcastFactory.generate(getFileUrl("sincast.xml")).get();
		persist(podcast);
		
		List<Episode> episodes = episodeRepository.findAll();
		for (Episode episode : episodes) {
			assertEquals("SinCast - Presented by CinemaSins", episode.getPodcast().getName());
			assertEquals("http://cinemasins.com/blog/", episode.getPodcast().getLink());
			assertNotNull(episode.getTitle());
			assertNotNull(episode.getLink());
			assertNotNull(episode.getFileUrl());
			assertNotNull(episode.getPublicationDate());
			assertNotNull(episode.getFileType());
			assertNotNull(episode.getFileLength());
		}
	}
	
	private String getFileUrl(String fileName) throws MalformedURLException {
		File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
		return file.toURI().toURL().toString();
	}
}
