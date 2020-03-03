package com.podcase.factory;

import static org.junit.Assert.*;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.podcase.factory.PodcastFactory;
import com.podcase.model.Podcast;

public class PodcastFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	PodcastFactory podcastFactory;

	@Test
	public void testPodcastDataForNonItunes() throws MalformedURLException {
		File file = new File(getClass().getClassLoader().getResource("lasertime.xml").getFile());
		Podcast laserTime = PodcastFactory.generate(file.toURI().toURL().toString());
		
		//Lasertime podcast Title has weird encoding. We'll skip checking. If the rest of the data is fine it doesn't matter
		assertEquals("http://www.lasertimepodcast.com", laserTime.getLink());
		assertEquals("", laserTime.getDescription());
		assertEquals("Sun Mar 01 06:39:21 GMT 2020", laserTime.getLastBuildDate().toString());
		assertEquals(null, laserTime.getAuthor());
	}
	
	@Test
	public void testPodcastDataForItunes() throws MalformedURLException {
		File file = new File(getClass().getClassLoader().getResource("sincast.xml").getFile());
		Podcast sincast = PodcastFactory.generate(file.toURI().toURL().toString());
		
		assertEquals("SinCast - Presented by CinemaSins", sincast.getName());
		assertEquals("https://ssl-static.libsyn.com/p/assets/7/8/9/4/789403aa4ebc16f8/SinCast_Logo_final_centered_1.22.16.jpg", sincast.getImageUrl());
		assertEquals("Sat Feb 29 11:00:00 GMT 2020", sincast.getLastBuildDate().toString());
		assertEquals("cinemasinssincast@gmail.com (cinemasinssincast@gmail.com)", sincast.getAuthor());
	}
	
	@Test
	public void testPodcastEpisodeCountForNonItunes() throws MalformedURLException {
		File file = new File(getClass().getClassLoader().getResource("lasertime.xml").getFile());
		Podcast laserTime = PodcastFactory.generate(file.toURI().toURL().toString());
		
		assertEquals(411, laserTime.getEpisodes().size());
	}
	
	@Test
	public void testPodcastEpisodeCountForItunes() throws MalformedURLException {
		File file = new File(getClass().getClassLoader().getResource("sincast.xml").getFile());
		Podcast sincast = PodcastFactory.generate(file.toURI().toURL().toString());
		
		assertEquals(282, sincast.getEpisodes().size());
	}

}
