package com.podcase.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.podcase.job.EpisodeDownloadJob;
import com.podcase.job.PodcastUpdateJob;

@Component
@ComponentScan(basePackages = "com.podcase")
public class PodcastScheduler {
	
	PodcastUpdateJob updateJob;
	
	EpisodeDownloadJob downloadJob;
	
	@Autowired
	public PodcastScheduler(PodcastUpdateJob updateJob, EpisodeDownloadJob downloadJob) {
		this.updateJob = updateJob;
		this.downloadJob = downloadJob;
	}
	
	@Scheduled(cron="0 0 */1 * * *")
	public void updateRepository() {
		updateJob.process();
	}
	
	@Scheduled(cron="0 */5 * * * *") //every 5 mins
	public void downloadEpisodes() {
		downloadJob.process();
	}
	
}
