package com.podcase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.podcase.projection.DeadDownloadProjection;
import com.podcase.repository.DeadDownloadRepository;

@RestController
public class DeadDownloadController {

	private DeadDownloadRepository deadDownloadRepository;

	@Autowired
	public DeadDownloadController(DeadDownloadRepository deadDownloadRepository) {
		this.deadDownloadRepository = deadDownloadRepository;
	}
	
	@GetMapping("/download/monitor")
	public List<DeadDownloadProjection> getDeadDownloads() {
		return deadDownloadRepository.findDeadDownloadsWithEpisodeData();
	}
	
}
