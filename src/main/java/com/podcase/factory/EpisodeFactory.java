package com.podcase.factory;

import java.util.List;
import java.util.Optional;

import com.podcase.model.Episode;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class EpisodeFactory {
	
	@SuppressWarnings("unchecked")
	public static Optional<Episode> generate(SyndEntryImpl entry) {
		if (entry.getEnclosures() == null || entry.getEnclosures().isEmpty()) {
			return Optional.empty();
		}
		Episode episode = new Episode();
		episode.setPublicationDate(entry.getPublishedDate());
		episode.setTitle(entry.getTitle());
		episode.setLink(entry.getLink());
		episode.setFileUrl(((List<SyndEnclosure>)entry.getEnclosures()).get(0).getUrl());
		episode.setFileType(((List<SyndEnclosure>)entry.getEnclosures()).get(0).getType());
		episode.setFileLength(((List<SyndEnclosure>)entry.getEnclosures()).get(0).getLength());
		episode.setGuid(entry.getUri());
		return Optional.of(episode);
	}

}
