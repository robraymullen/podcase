package com.podcase.utilities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.podcase.model.Episode;

class EpisodeFileCleanerTest {

	Episode episode;
	
	EpisodeFileCleaner cleaner;
	
	@BeforeEach
	void setUp() throws Exception {
		cleaner = new EpisodeFileCleaner();
		episode = new Episode();
		episode.setTitle("episode");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testGetFileNameFromSimpleUrl() {
		episode.setFileUrl("https://website.com/file-name.mp3");
		episode.setGuid("1234");
		assertEquals("1234_file-name.mp3", cleaner.getFileName(episode));
	}
	
	@Test
	public void testGetFileNameFromUrlWithSimpleParameters() {
		episode.setFileUrl("https://website.com/file-name.mp3?param1=1&param2=2");
		episode.setGuid("1234");
		assertEquals("1234_file-name.mp3", cleaner.getFileName(episode));
	}
	
	@Test
	public void testGetFileNameFromUrlWithComplexParameters() {
		episode.setFileUrl("https://website.com/file-name.mp3?param=1.12345&file=.wav&second=h.mp4");
		episode.setGuid("1234");
		assertEquals("1234_file-name.mp3", cleaner.getFileName(episode));
	}
	
	@Test
	public void testGetFileNameFromUrlWithIncorrectQuestionsMarks() {
		episode.setFileUrl("https://website.com/file-name.mp3?param=1.12345&file=.w?av&second=h.m?p4");
		episode.setGuid("1234");
		assertEquals("1234_file-name.mp3", cleaner.getFileName(episode));
	}

}
