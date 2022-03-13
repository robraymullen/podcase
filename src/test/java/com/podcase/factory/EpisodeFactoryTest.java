package com.podcase.factory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.podcase.model.Episode;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

@RunWith(MockitoJUnitRunner.class)
public class EpisodeFactoryTest {
	
	@Mock
	SyndEntryImpl entry;
	
	@Mock
	SyndEnclosure enclosure;
	
	@Mock
	SyndContent content;
	
	@Mock
	List<SyndEnclosure> enclosureList;

	@Before
	public void setUp() throws Exception {
		Date date = new Date();
		MockitoAnnotations.initMocks(this.getClass());
		doReturn(date).when(entry).getPublishedDate();
		doReturn("description").when(content).getValue();
		doReturn(content).when(entry).getDescription();
		doReturn("Title").when(entry).getTitle();
		doReturn("http://link.com").when(entry).getLink();
		doReturn(enclosureList).when(entry).getEnclosures();
		doReturn(enclosure).when(enclosureList).get(0);
		doReturn("file://myFile.mp3").when(enclosure).getUrl();
		doReturn("audio/mp3").when(enclosure).getType();
		doReturn(12345L).when(enclosure).getLength();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEpisodePropertiesAreSetCorrectly() { 
		Episode episode = EpisodeFactory.generate(entry).get();
		
		assertEquals("Title", episode.getTitle());
		assertEquals("http://link.com", episode.getLink());
		assertEquals("file://myFile.mp3", episode.getFileUrl());
		assertEquals("audio/mp3", episode.getFileType());
		assertEquals(Long.valueOf(12345), episode.getFileLength());
		assertEquals("description", episode.getDescription());
	}
	
	@Test
	public void testThatAnEmptyOptionalIsReturnedWhenEntryHasNullEnclosure() {
		doReturn(null).when(entry).getEnclosures();
		assertEquals(Optional.empty(), EpisodeFactory.generate(entry));
	}
	
	@Test
	public void testThatAnEmptyOptionalIsReturnedWhenEntryHasEmptyEnclosure() {
		doReturn(new ArrayList<>()).when(entry).getEnclosures();
		assertEquals(Optional.empty(), EpisodeFactory.generate(entry));
	}
	
	@Test
	public void testPropertiesFromRssFileWithItunes() throws IllegalArgumentException, FeedException, IOException {
		URL feedSource = new URL(getFileUrl("sincast.xml"));
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(feedSource));
		@SuppressWarnings("unchecked")
		List<SyndEntryImpl> entries = feed.getEntries();
		
		Episode episode = EpisodeFactory.generate(entries.get(0)).get();
		assertEquals("<div>The SinCast crew dives into the murky waters of TV adaptations this week with Fantasy Island. "
				+ "Does Michael Pena deliver a suitable Montalban? Are Lucy Hale and Maggie Q underrated and underutilized in film? Are you hoping to hear references to 2000's Bedazzled after you press play? Find out on an all new MINIPOD!!!</div> <div> </div> <div> <div class=\"def\"> <div class=\"yj6qo ajU\"> <div class=\"def\"> <div class=\"def\"> <div> <div class=\"def\"> <div class=\"def\"> <div class=\"def\"> <div class=\"def\"> <div class=\"yj6qo ajU\"> <div class=\"def\"> <div class=\"yj6qo\"> <div class=\"def\"> <div class=\"yj6qo ajU\">We'll be back next Monday for a new episode of SinCast, and keep in touch! Tweet us @cinemasins, comment on SoundCloud, (<a href= \"https://soundcloud.com/cinemasins\">https://soundcloud.com/cinemasins</a>) subscribe to the subreddit (<a href= \"https://www.reddit.com/r/CinemaSins/\">https://www.reddit.com/r/CinemaSins/</a>) and email us at <a href= \"mailto:cinemasinssincast@gmail.com\">cinemasinssincast@gmail.com</a>.</div> </div> </div> </div> </div> </div> </div> </div> </div> "
				+ "</div> </div> </div> </div> </div> <div class=\"def\"> </div> </div>", episode.getDescription());
		assertEquals("SinCast - FANTASY ISLAND - Bonus Episode!", episode.getTitle());
		assertEquals(Long.valueOf(56098793), episode.getFileLength());
		assertEquals("film,movies,horror,criticism,blumhouse", episode.getKeywords());
		assertEquals("\"The SinCast crew dives into the murky waters of TV adaptations this week with Fantasy Island. "
				+ "Does Michael Pena deliver a suitable Montalban? Are Lucy Hale and Maggie Q underrated and underutilized in film? "
				+ "Are you hoping to hear references "
				+ "to 2000's Bedazzled after you press play? Find out on an all new MINIPOD!!!\"", episode.getSummary());
	}
	
	private String getFileUrl(String fileName) throws MalformedURLException {
		File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
		return file.toURI().toURL().toString();
	}

}
