package com.podcase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import com.podcase.model.DeadDownload;
import com.podcase.repository.DeadDownloadRepository;

@Repository
public class DeadDownloadController {

	private DeadDownloadRepository deadDownloadRepository;

	@Autowired
	public DeadDownloadController(DeadDownloadRepository deadDownloadRepository) {
		this.deadDownloadRepository = deadDownloadRepository;
	}
	
	@GetMapping("deadDownloads")
	public List<DeadDownload> getDeadDownloads() {
		return deadDownloadRepository.findAll();
	}
	
}
