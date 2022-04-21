package com.podcase.job;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.podcase.job.EpisodeDownloadJob;
import com.podcase.model.Episode;
import com.podcase.repository.EpisodeRepository;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "com.podcase")
@DataJpaTest
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
class EpisodeDownloadJobTest {
	
	Episode episode;
	
	@Autowired
	EpisodeRepository repository;
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	@Qualifier("downloadJob")
	EpisodeDownloadJob downloadJob;
	
	@Value("${audio.file.store}")
	String audioStore;

	@BeforeEach
	void setUp() throws Exception {
		episode = new Episode();
		episode.setTitle("episode title");
		episode.setLink("link");
		//Taken from lasertime.xml
//		episode.setFileUrl("http://www.podtrac.com/pts/redirect.mp3/traffic.libsyn.com/lasertime/LaserTime_Ep377_0418.mp3");
		episode.setFileUrl("https://stream.redcircle.com/episodes/d790d398-85a9-42b1-91ae-e6ad1f270b29/stream.mp3");
		episode.setDescription("description");
		episode.setPublicationDate(new Date());
		episode.setGuid("guid");
	}

	@AfterEach
	void tearDown() throws Exception {
		//TODO fix directory setup for downloading a podcast in this test, as its parent folder should be the name of the podcast
		FileUtils.cleanDirectory(new File(System.getProperty("user.dir")+audioStore));
	}

	/*
	 * Leaving as ignored to reduce build time.
	 * Might reactivate and tie it to a very small file
	 */
//	@Ignore
//	@Test
//	void testFileDownload() {
//		persist(episode);
//		downloadJob.process();
//		assertEquals(1, FileUtils.listFiles(new File(System.getProperty("user.dir")+audioStore), 
//				TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).size());
//		assertEquals(0, repository.findByDownloaded(false).size());
//	}
	
	protected void persist(Object entity) {
		entityManager.persist(entity);
        entityManager.flush();
	}

}
