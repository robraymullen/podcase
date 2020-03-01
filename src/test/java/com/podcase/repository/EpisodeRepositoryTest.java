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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSingleEpisodeById() {
		persist(episode);
		List<Episode> episodes = episodeRepository.findAll();
		Long id = new Long(episodes.get(0).getId());
		Optional<Episode> actualEpisode = episodeRepository.findById(id);
		assertEquals(id, actualEpisode.get().getId());
	}

}
