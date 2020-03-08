package com.podcase;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class PodcaseApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PodcaseApplication.class, args);
	}
}
