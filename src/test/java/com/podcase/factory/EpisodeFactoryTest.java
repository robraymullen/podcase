package com.podcase.factory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

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
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntryImpl;

@RunWith(MockitoJUnitRunner.class)
public class EpisodeFactoryTest {
	
	@Mock
	SyndEntryImpl entry;
	
	@Mock
	SyndEnclosure enclosure;
	
	@Mock
	List<SyndEnclosure> enclosureList;

	@Before
	public void setUp() throws Exception {
		Date date = new Date();
		MockitoAnnotations.initMocks(this.getClass());
		doReturn(date).when(entry).getPublishedDate();
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
		
		assertEquals("Title", entry.getTitle());
		assertEquals("http://link.com", episode.getLink());
		assertEquals("file://myFile.mp3", episode.getFileUrl());
		assertEquals("audio/mp3", episode.getFileType());
		assertEquals(new Long(12345), episode.getFileLength());
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

}
