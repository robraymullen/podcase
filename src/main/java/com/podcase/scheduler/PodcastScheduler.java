package com.podcase.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.podcase.job.PodcastUpdateJob;

@Component
@ComponentScan(basePackages = "com.podcase")
public class PodcastScheduler {
	
	@Autowired
	PodcastUpdateJob updateJob;
	
	@Scheduled(cron="0 0 */1 * * *")
	@Autowired
	public void updateRepository() {
		updateJob.process();
	}
	
}
