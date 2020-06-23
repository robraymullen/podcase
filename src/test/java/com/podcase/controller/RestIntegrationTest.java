package com.podcase.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.podcase.model.Episode;
import com.podcase.model.Podcast;
import com.podcase.repository.EpisodeRepository;
import com.podcase.repository.PodcastRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
@Transactional
public class RestIntegrationTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private PodcastRepository podcastRepository;
	
	@Autowired
	private EpisodeRepository episodeRepository;
	
	@AfterEach
	public void tearDown() {
		podcastRepository.deleteAll();
		podcastRepository.flush();
	}
	
	@Test
	public void testAddPodcast() throws Exception {
		mvc.perform(put("/podcasts")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"podcastUrl\": \"http://www.lasertimepodcast.com/category/lasertimepodcast/feed/\"}"))
				.andExpect(status().isOk());
		
		assertEquals(1, podcastRepository.count());
		assertTrue(episodeRepository.count() > 0);
	}
	
	@Test
	public void testGetAllPodcasts() throws Exception {
		mvc.perform(put("/podcasts")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"podcastUrl\": \"http://www.lasertimepodcast.com/category/lasertimepodcast/feed/\"}"))
				.andExpect(status().isOk());
		
		mvc.perform(get("/podcasts")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$[0].episodes").exists())
			      .andExpect(MockMvcResultMatchers.jsonPath("$[0].episodes").isArray())
			      .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
				  .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber());
	}
	
	@Test
	public void testGetPodcastById() throws Exception {
		mvc.perform(put("/podcasts")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"podcastUrl\": \"http://www.lasertimepodcast.com/category/lasertimepodcast/feed/\"}"))
				.andExpect(status().isOk());
		
		List<Podcast> podcasts = podcastRepository.findAll();
		Long id = podcasts.get(0).getId();
		
		mvc.perform(get("/podcasts/"+id)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.episodes").exists())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.episodes").isArray())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
				  .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber());
	}
	
	@Test
	public void testGetEpisodeById() throws Exception {
		mvc.perform(put("/podcasts")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"podcastUrl\": \"http://www.lasertimepodcast.com/category/lasertimepodcast/feed/\"}"))
				.andExpect(status().isOk());
		
		List<Episode> episodes = episodeRepository.findAll();
		Long id = episodes.get(0).getId();
		
		mvc.perform(get("/episodes/"+id)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
				  .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber());
	}

}
