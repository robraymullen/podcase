package com.podcase.projection;

import java.util.Date;

public interface DeadDownloadProjection {

	Long getId();
	
	Integer getAttemptCount();
	
	Date getLastDownloadAttempt();
	
	Long getEpisodeId();
	
	String getFileUrl();
	
	String getTitle();
}
