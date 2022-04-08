package com.podcase.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {
	
	public static String PODCAST_AUDIO_PATH = "/podcast/audio/"; 
	
	public static String IMAGE_PATH = "/images/";
	
	@Value("${audio.file.store}")
	String audioStore;

	@Value("${image.file.store}")
	String imageStore;
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PODCAST_AUDIO_PATH+"**").addResourceLocations("file:"+System.getProperty("user.dir")+audioStore);
        registry.addResourceHandler(IMAGE_PATH+"**").addResourceLocations("file:"+System.getProperty("user.dir")+imageStore);
    }

}
