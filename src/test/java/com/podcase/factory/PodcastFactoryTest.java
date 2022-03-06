package com.podcase.factory;

import static org.junit.Assert.*;

import java.io.File;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.podcase.factory.PodcastFactory;
import com.podcase.model.Podcast;

public class PodcastFactoryTest {

	//"Sun Mar 01 06:39:21 GMT 2020"
	SimpleDateFormat localDateFormat = new SimpleDateFormat("EE MMM d HH:mm:ss z yyyy");
	
	
	@Before
	public void setUp() throws Exception {
		localDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	PodcastFactory podcastFactory;

	@Test
	public void testPodcastDataForNonItunes() throws MalformedURLException, ParseException {
		Podcast laserTime = PodcastFactory.generate(getFileUrl("lasertime.xml")).get();
		
		//Lasertime podcast Title has weird encoding. We'll skip checking. If the rest of the data is fine it doesn't matter
		assertEquals("", laserTime.getDescription());
		assertEquals(localDateFormat.format((localDateFormat.parse("Sun Mar 01 06:39:21 GMT 2020"))), localDateFormat.format(laserTime.getLastBuildDate()));
		assertEquals(null, laserTime.getAuthor());
	}
	
	@Test
	public void testPodcastDataForItunes() throws MalformedURLException, ParseException {
		Podcast sincast = PodcastFactory.generate(getFileUrl("sincast.xml")).get();
		
		assertEquals("SinCast - Presented by CinemaSins", sincast.getName());
		assertEquals("https://ssl-static.libsyn.com/p/assets/7/8/9/4/789403aa4ebc16f8/SinCast_Logo_final_centered_1.22.16.jpg", sincast.getImageUrl());
		assertEquals(localDateFormat.format(localDateFormat.parse("Sat Feb 29 11:00:00 GMT 2020")), localDateFormat.format(sincast.getLastBuildDate()));
		assertEquals("cinemasinssincast@gmail.com (cinemasinssincast@gmail.com)", sincast.getAuthor());
	}
	
	@Test
	public void testPodcastDataForItunesForeignMarkup() throws MalformedURLException, ParseException {
		Podcast recotopia = PodcastFactory.generate(getFileUrl("recotopia.xml")).get();
		
		assertEquals("Recotopia", recotopia.getName());
		assertEquals("https://media.redcircle.com/images/2022/1/31/20/2ef6cdb4-456f-4561-af10-ae3de4959060_9207171d62_sincast_logo_final_centered_1.22.16.jpg", recotopia.getImageUrl());
		assertEquals("CinemaSins | Chris Atkinson & Jeremy Scott", recotopia.getAuthor());
	}
	
	@Test
	public void testPodcastEpisodeCountForNonItunes() throws MalformedURLException {
		Podcast laserTime = PodcastFactory.generate(getFileUrl("lasertime.xml")).get();
		
		assertEquals(411, laserTime.getEpisodes().size());
	}
	
	@Test
	public void testPodcastEpisodeCountForItunes() throws MalformedURLException {
		Podcast sincast = PodcastFactory.generate(getFileUrl("sincast.xml")).get();
		
		assertEquals(282, sincast.getEpisodes().size());
	}
	
	@Test
	public void testGetNewEpisodesForPodcastCountNonItunes() throws MalformedURLException {
		Podcast lasertime = PodcastFactory.generate(getFileUrl("lasertime.xml")).get();
		lasertime.setRssFeed(getFileUrl("lasertime-updated.xml"));
		
		assertEquals(1, PodcastFactory.getNewEpisodes(lasertime).size());
	}
	
	@Test
	public void testGetNewEpisodesForPodcastCountItunes() throws MalformedURLException {
		Podcast sincast = PodcastFactory.generate(getFileUrl("sincast.xml")).get();
		sincast.setRssFeed(getFileUrl("sincast-updated.xml"));
		
		assertEquals(2, PodcastFactory.getNewEpisodes(sincast).size());
	}
	
	@Test
	public void testPodcastIsNotPresentWhenUrlIsIncorrect() {
		assertTrue(PodcastFactory.generate("http://google.com/rss.xml").isEmpty());
	}
	
	@Test
	public void testValueIsPresentWhenUrlIsCorrect() throws MalformedURLException {
		assertTrue(PodcastFactory.generate(getFileUrl("lasertime.xml")).isPresent());
	}
	
	private String getFileUrl(String fileName) throws MalformedURLException {
		File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
		return file.toURI().toURL().toString();
	}

}
