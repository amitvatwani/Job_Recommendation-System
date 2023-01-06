package com.jr.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import javax.lang.model.util.Elements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jr.model.Job;
@Service
public class JobsInfoCrawler {
	
	@Autowired
	private JobService jobService;
	public void getJobsInfo() {
		jobService.truncateTable();
		try {
  	      // Here we create a document object and use JSoup to fetch the website
  		
  		for(int i=1; i<=188; i++) {
  			System.out.println("Page No: " + i);
  	      Document doc = Jsoup.connect("https://internshala.com/internships/page-"+i).userAgent("Mozilla").timeout(20000).get();
  	      // With the document fetched, we use JSoup's title() method to fetch the title
  	     System.out.printf("Title: %s\n", doc.title());

  	      org.jsoup.select.Elements internships = doc.getElementsByClass("internship_meta");
  	      org.jsoup.select.Elements link = doc.getElementsByClass("button_container_card ");
  	      
  	      for(int j=0; j<internships.size(); j++) {
  	        // Extract the title
  	    	org.jsoup.select.Elements profile = internships.get(j).getElementsByClass("profile");
  	    	org.jsoup.select.Elements company = internships.get(j).getElementsByClass("company_and_premium");
  	    	org.jsoup.select.Elements location = internships.get(j).getElementsByClass("location_link");
  	    	String JobTitle="";
  	    	String companyName="";
  	    	String locationName="";
  	    	String url="";
  	    	for (Element prof : profile) {
  	    		JobTitle = prof.getElementsByClass("view_detail_button").text();
  	    		//url += prof.getElementsByClass("view_detail_button").attr("href");
  	    	}
  	    	for (Element comp : company) {
  	    		companyName = comp.getElementsByClass("view_detail_button").text();
  	    	}
  	    	for (Element loc : location) {
  	    		locationName = loc.text();
  	    	}
  	    	
  	    	//System.out.println(companyName + " - " + JobTitle + " - " + locationName);
  	    	  org.jsoup.select.Elements cont = link.get(j).getElementsByClass("cta_container");
  	    	  
  	    	  for(Element c: cont) {
  	    		  org.jsoup.select.Elements applyLink = c.getElementsByClass("view_detail_button_outline");
  	    		  url="https://internshala.com";
  	    		  for(Element redirectLink: applyLink) {
  	    			  url+=redirectLink.attr("href");
  	    			  
  	    		  }
  	    		  
  	    	  }
  	    	  String[] Joblocations = locationName.split(",");
  	    	  List<String> locations = Arrays.asList(Joblocations);
  	    	  Job job = new Job(JobTitle, companyName, locations, url);
  	    	  jobService.saveJob(job);
  	    	  //Job resJob = jobService.
  	    	//System.out.println(companyName + " - " + JobTitle + " - " + locationName + " - " + url);
  	    	//System.out.println();
  	      }
  		}
  		jobService.callMailService();
  	    // In case of any IO errors, we want the messages written to the console
  	    } catch (IOException e) {
  	      e.printStackTrace();
  	    }
	}
	
	public void printAll() {
		List<Job> list = jobService.getAllJobs();
		for(Job j: list) {
			System.out.println(j.getCompanyName() + " - " + j.getJobTitle() + " - " + j.getLocations() + " - " + j.getUrl());
		}
	}
}
