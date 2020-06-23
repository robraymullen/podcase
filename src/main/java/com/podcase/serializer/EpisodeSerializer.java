package com.podcase.serializer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.podcase.model.Episode;

public class EpisodeSerializer extends StdSerializer<List<Episode>> {
	
	public EpisodeSerializer() {
        this(null);
    }

	protected EpisodeSerializer(Class<List<Episode>> episode) {
		super(episode);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(List<Episode> episodes, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartArray();
    	for (Episode episode : episodes) {
    		generator.writeStartObject();
    		generator.writeFieldName("id");
    		generator.writeNumber(episode.getId());
    		generator.writeFieldName("name");
    		generator.writeString(episode.getTitle());
    		generator.writeEndObject();
        }
    	generator.writeEndArray();
		
	}

}
