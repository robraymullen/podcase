package com.podcase.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jdom.Content;
import org.jdom.Element;

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
		episode.setDescription(entry.getDescription().getValue());
		episode.setFileUrl(((List<SyndEnclosure>)entry.getEnclosures()).get(0).getUrl());
		episode.setFileType(((List<SyndEnclosure>)entry.getEnclosures()).get(0).getType());
		episode.setFileLength(((List<SyndEnclosure>)entry.getEnclosures()).get(0).getLength());
		episode.setGuid(entry.getUri());
		
		if (entry.getForeignMarkup() != null) {
			try {
				List<Element> foreignElements = (ArrayList) entry.getForeignMarkup();
				for (Element element : foreignElements) {
					String name = element.getName();
					List<Content> contentList = element.getContent();
					switch (name) {
						case "keywords":
							episode.setKeywords(contentList.get(0).getValue());
							break;
						case "image":
							episode.setImageUrl(element.getAttributeValue("href"));
							break;
						case "summary":
							episode.setSummary(contentList.get(0).getValue());
							break;
						case "duration":
							//TODO cleanup
							String duration = contentList.get(0).getValue();
							if (duration.contains(":")) {
								String hours, minutes, seconds;
								String[] splits = duration.split(":");
								if (splits.length > 2) {
									hours = splits[0];
									minutes = splits[1];
									seconds = splits[2];
								} else {
									hours = "0";
									minutes = splits[0];
									seconds = splits[1];
								}
								int totalSeconds = (Integer.parseInt(hours)*60*60) + (Integer.parseInt(minutes) * 60) + Integer.parseInt(seconds);
								duration = String.valueOf(totalSeconds);
							}
							episode.setDuration(Integer.parseInt(duration));
					}
				}
			} catch (Exception e) {
				
			}
		}
		
		return Optional.of(episode);
	}

}
