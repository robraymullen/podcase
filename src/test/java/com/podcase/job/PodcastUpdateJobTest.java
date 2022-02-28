package com.podcase.job;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.podcase.factory.PodcastFactory;
import com.podcase.job.ScheduledJob;
import com.podcase.model.Episode;
import com.podcase.model.Podcast;
import com.podcase.repository.AbstractRepositoryTest;
import com.podcase.repository.EpisodeRepository;
import com.podcase.repository.PodcastRepository;

//without the componentscan the test can't find the scheduledjob as it's in a different package
@ComponentScan(basePackages = "com.podcase")
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
class PodcastUpdateJobTest extends AbstractRepositoryTest {
	
	@Autowired
	PodcastRepository podcastRepository;
	
	@Autowired
	EpisodeRepository episodeRepository;
	
	@Autowired
	@Qualifier("podcastUpdateJob")
	ScheduledJob updateJob;
	
	Podcast podcast;

	@Before
	void setUp() throws Exception {
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
	void tearDown() throws Exception {
	}

	@Transactional
	@Test
	public void testEpisodeCountAfterUpdateNonItunes() throws MalformedURLException {
		podcast = PodcastFactory.generate(getFileUrl("lasertime.xml")).get();
		persist(podcast);
		podcast.setRssFeed(getFileUrl("lasertime-updated.xml"));
		update(podcast);
		updateJob.process();
		
		List<Episode> episodes = episodeRepository.findAll();
		assertEquals(412, episodes.size());
	}
	
	@Transactional
	@Test
	public void testNewEpisodePropertiesAfterUpdateNonItunes() throws MalformedURLException {
		podcast = PodcastFactory.generate(getFileUrl("lasertime.xml")).get();
		persist(podcast);
		podcast.setRssFeed(getFileUrl("lasertime-updated.xml"));
		update(podcast);
		updateJob.process();
		
		List<Episode> episodes = episodeRepository.findAllByOrderByIdDesc();
		Episode latestEpisode = episodes.get(0);
		assertEquals("new-updated-guid", latestEpisode.getGuid());
		assertEquals("Latest episode addition to lasertime #401", latestEpisode.getTitle());
		assertEquals("http://www.lasertimepodcast.com/2020/02/26/latest-episode-addition/", latestEpisode.getLink());
	}
	
	@Transactional
	@Test
	public void testEpisodeCountAfterUpdateItunes() throws MalformedURLException {
		podcast = PodcastFactory.generate(getFileUrl("sincast.xml")).get();
		persist(podcast);
		podcast.setRssFeed(getFileUrl("sincast-updated.xml"));
		update(podcast);
		updateJob.process();
		
		List<Episode> episodes = episodeRepository.findAll();
		assertEquals(284, episodes.size());
	}
	
	@Transactional
	@Test
	public void testNewEpisodePropertiesAfterUpdateItunes() throws MalformedURLException {
		podcast = PodcastFactory.generate(getFileUrl("sincast.xml")).get();
		persist(podcast);
		podcast.setRssFeed(getFileUrl("sincast-updated.xml"));
		update(podcast);
		updateJob.process();
		
		List<Episode> episodes = episodeRepository.findAllByOrderByIdDesc();
		Episode latestEpisode = episodes.get(0);
		Episode secondLatestEpisode = episodes.get(1);
		assertEquals("new-sincast-guid", latestEpisode.getGuid());
		assertEquals("New sincast", latestEpisode.getTitle());
		assertEquals("https://cinemasins.libsyn.com/sincast-fantasy-island-bonus-episode", latestEpisode.getLink());
		
		assertEquals("new-sincast-guid2", secondLatestEpisode.getGuid());
		assertEquals("New sincast2", secondLatestEpisode.getTitle());
		assertEquals("https://cinemasins.libsyn.com/sincast-fantasy-island-bonus-episode", secondLatestEpisode.getLink());
	}
	
	@Test
	@Transactional
	public void testEpisodeCountAfterAddingTwoPodcasts() throws MalformedURLException {
		podcast = PodcastFactory.generate(getFileUrl("sincast.xml")).get();
		persist(podcast);
		podcast.setRssFeed(getFileUrl("sincast-updated.xml"));
		update(podcast);
		Podcast podcast2 = PodcastFactory.generate(getFileUrl("lasertime.xml")).get();
		persist(podcast2);
		podcast2.setRssFeed(getFileUrl("lasertime-updated.xml"));
		update(podcast2);
		
		updateJob.process();
		List<Episode> episodes = episodeRepository.findAll();
		assertEquals(696, episodes.size());
	}
	
	@Transactional
	@Test
	public void testEpisodeCountPerPodcastAfterUpdatingWithTwoPodcasts() throws MalformedURLException {
		podcast = PodcastFactory.generate(getFileUrl("sincast.xml")).get();
		persist(podcast);
		podcast.setRssFeed(getFileUrl("sincast-updated.xml"));
		update(podcast);
		Podcast podcast2 = PodcastFactory.generate(getFileUrl("lasertime.xml")).get();
		persist(podcast2);
		podcast2.setRssFeed(getFileUrl("lasertime-updated.xml"));
		update(podcast2);
		
		updateJob.process();
		List<Podcast> podcasts = podcastRepository.findAll();
		assertEquals(284, podcasts.get(0).getEpisodes().size());
		assertEquals(412, podcasts.get(1).getEpisodes().size());
	}
	
	private String getFileUrl(String fileName) throws MalformedURLException {
		File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
		return file.toURI().toURL().toString();
	}

}
