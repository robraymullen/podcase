package com.podcase.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {
	
	public static String PODCAST_AUDIO_PATH = "/podcast/audio/"; 
	
	@Value("${audio.file.store}")
	String audioStore;
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PODCAST_AUDIO_PATH+"**").addResourceLocations("file:"+System.getProperty("user.dir")+audioStore);
    }

}
