package com.podcase.factory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;
import com.podcase.model.Episode;
import com.podcase.model.Podcast;
import com.podcase.factory.EpisodeFactory;

public class PodcastFactory {
	
	public static Podcast generate(String url) {
		try {
			URL feedSource = new URL(url);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedSource));
			Podcast podcast = new Podcast();
			podcast.setName(feed.getTitle());
			podcast.setDescription(feed.getDescription());
			podcast.setLastBuildDate(feed.getPublishedDate());
			podcast.setAuthor(feed.getAuthor());
			podcast.setLink(feed.getLink());
			if(feed.getImage() != null) {
				podcast.setImageUrl(feed.getImage().getUrl());
			}
			@SuppressWarnings("unchecked")
			List<SyndEntryImpl> entries = feed.getEntries();
			for(SyndEntryImpl entry : entries) {
				Optional<Episode> episode = EpisodeFactory.generate(entry);
				if (episode.isPresent()) {
					podcast.addEpisode(episode.get());
				}
			}
			return podcast;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

}