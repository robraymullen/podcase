package com.podcase.factory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Text;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;
import com.podcase.model.Episode;
import com.podcase.model.Podcast;
import com.podcase.factory.EpisodeFactory;

public class PodcastFactory {
	
	public static Optional<Podcast> generate(String url) {
		try {
			URL feedSource = new URL(url);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed;
			try {
				feed = input.build(new XmlReader(feedSource));
			} catch (FileNotFoundException e) {
				// TODO add logging here.
				return Optional.empty();
			}
			
			Podcast podcast = new Podcast();
			podcast.setName(feed.getTitle());
			podcast.setDescription(feed.getDescription());
			podcast.setLastBuildDate(feed.getPublishedDate());
			podcast.setAuthor(feed.getAuthor());
			podcast.setLink(feed.getLink());
			podcast.setRssFeed(url);
			
			feed.getForeignMarkup();
			if(feed.getImage() != null) {
				podcast.setImageUrl(feed.getImage().getUrl());
			}
			@SuppressWarnings("unchecked")
			List<SyndEntryImpl> entries = feed.getEntries();
			
			if (feed.getForeignMarkup() != null) {
				try {
					List<Element> foreignElements = (ArrayList) feed.getForeignMarkup();
					for (Element element : foreignElements) {
						String name = element.getName();
						switch (name) {
							case "image":
								if (podcast.getImageUrl() == null || podcast.getImageUrl().isEmpty() ) {
									podcast.setImageUrl(element.getAttribute("href").getValue());
								}
								break;
							case "author":
								if (podcast.getAuthor() == null || podcast.getAuthor().isEmpty()) {
									List<Content> contentList = element.getContent();
									podcast.setAuthor(contentList.get(0).getValue());
								}
								break;
						}
					}
				} catch (Exception e) {
					
				}
			}
			// TODO reverse order podcast additions?
			List<Episode> episodes = new ArrayList<>();
			for(SyndEntryImpl entry : entries) {
				Optional<Episode> episode = EpisodeFactory.generate(entry);
				if (episode.isPresent()) {
					episodes.add(episode.get());
					podcast.getEpisodeGuids().add(episode.get().getGuid());
				}
			}
			Collections.reverse(episodes);
			podcast.addAllEpisodes(episodes);
			return Optional.of(podcast);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public static List<Episode> getNewEpisodes(Podcast podcast) {
		try {
			List<Episode> newEpisodes = new ArrayList<>(); 
			Optional<Podcast> retrievedPodcast = PodcastFactory.generate(podcast.getRssFeed());
			Podcast updatedPodcast;
			if (retrievedPodcast.isPresent()) {
				updatedPodcast = retrievedPodcast.get();
			} else {
				// TODO more logging here
				return newEpisodes;
			}
			for (Episode episode : updatedPodcast.getEpisodes()) {
				if (!podcast.getEpisodeGuids().contains(episode.getGuid())) {
					newEpisodes.add(episode);
				}
			}
			/*
			 * Podcasts are retrieved top down from the feed. But they need to be added bottom up
			 * to ensure the id sequence is correct.
			 * The newest episode should be the one added last.
			 */
			return newEpisodes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Episode>();
	}
	

}
