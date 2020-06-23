package com.podcase.serializer;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.podcase.model.Episode;
import com.podcase.model.Podcast;

public class PodcastSerializer extends JsonSerializer<Podcast> {
	
	public PodcastSerializer() {
        this(null);
    }

    public PodcastSerializer(Podcast t) {
        super();
    }
	@Override
	public void serialize(Podcast podcast, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("id", podcast.getId());
		jsonGenerator.writeStringField("title", podcast.getName());
        jsonGenerator.writeEndObject();
	}

}
