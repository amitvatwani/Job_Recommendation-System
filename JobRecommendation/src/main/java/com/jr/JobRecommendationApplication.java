package com.jr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jr.service.JobService;
import com.jr.service.JobsInfoCrawler;

@SpringBootApplication
@EnableScheduling
public class JobRecommendationApplication {
	@Autowired
	private JobsInfoCrawler jobInfoCrawler;
	public static void main(String[] args) {
		//SpringApplication.run(JobRecommendationApplication.class, args);
		//JobsInfoCrawler.getJobsInfo();
		ApplicationContext context = SpringApplication.run(JobRecommendationApplication.class, args);
		JobsInfoCrawler service = (JobsInfoCrawler)context.getBean("jobsInfoCrawler");
		//service.getJobsInfo();
		//service.printAll();
	}

}
