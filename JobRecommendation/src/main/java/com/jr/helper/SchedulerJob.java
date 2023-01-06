package com.jr.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jr.service.JobsInfoCrawler;

@Component
public class SchedulerJob {
	@Autowired
	private JobsInfoCrawler jobsInfoCrawler;
	@Scheduled(fixedDelay = 3600000, initialDelay = 5000)
	public void fetchDataCronJob() {
		jobsInfoCrawler.getJobsInfo();	
	}
}
