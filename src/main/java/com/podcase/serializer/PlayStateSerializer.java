package com.podcase.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.podcase.model.PlayState;
import com.podcase.model.Podcast;

public class PlayStateSerializer extends JsonSerializer<PlayState> {
	
	public PlayStateSerializer() {
        this(null);
    }

    public PlayStateSerializer(PlayState t) {
        super();
    }
	@Override
	public void serialize(PlayState playState, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("id", playState.getId());
		jsonGenerator.writeNumberField("episodeId", playState.getEpisode().getId());
		jsonGenerator.writeNumberField("userId", playState.getUser().getId());
		if (playState.getPlayLength() != null) {
			jsonGenerator.writeNumberField("playLength", playState.getPlayLength());
		} else {
			jsonGenerator.writeNumberField("playLength", 0);
		}
		jsonGenerator.writeNumberField("lastPlayed", playState.getLastPlayed().getTime());
        jsonGenerator.writeEndObject();
	}

}
