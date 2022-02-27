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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.podcase.model.Episode;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class EpisodeRepositoryTest extends AbstractRepositoryTest {
	
	@Autowired
	EpisodeRepository episodeRepository;
	
	Episode episode;

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

	@Rollback
	@After
	public void tearDown() throws Exception {
	}

	@Transactional
	@Test
	public void testGetSingleEpisodeById() {
		persist(episode);
		List<Episode> episodes = episodeRepository.findAll();
		Long id = Long.valueOf(episodes.get(0).getId());
		Optional<Episode> actualEpisode = episodeRepository.findById(id);
		assertEquals(id, actualEpisode.get().getId());
	}
	
	@Transactional
	@Test
	public void testFindingAllEpisodesThatAreNotDownloaded() {
		persist(episode);
		Episode ep2 = new Episode();
		ep2.setTitle("ep2 title");
		ep2.setLink("link");
		ep2.setFileUrl("fileUrl");
		ep2.setDescription("description");
		ep2.setPublicationDate(new Date());
		ep2.setGuid("guid");
		persist(ep2);
		
		List<Episode> episodes = episodeRepository.findByDownloaded(false);
		assertEquals(2, episodes.size());
	}

}
