package com.jr.service;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.jr.model.Job;
import com.jr.model.User;
import com.jr.repo.JobRepo;

import jakarta.mail.internet.MimeMessage;

@Service
public class JobService {
	
	@Autowired
	private JobRepo jobRepo;
	
	@Autowired UserService userService;
	
	@Autowired 
	private JavaMailSender javaMailSender;
	
	public Job saveJob(Job job) {
		//System.out.println("here");
		Job savedJob = jobRepo.save(job);
		return savedJob;
	}
	
	public List<Job> getAllJobs(){
		return jobRepo.findAll();
	}
	
	public void truncateTable() {
		jobRepo.truncateTable();
	}
	
	public Set<Job> searchJobs(Set<String> jobKeyword){
		Set<Job> jobs = new HashSet();
		for(String keyword : jobKeyword)
			jobs.addAll(jobRepo.findTop10ByJobTitleContainingIgnoreCase(keyword));
		return jobs;
	}

	public void callMailService() {
		List<User> users = userService.getAllUsers();
		for(User user: users) {
			sendMail(user);
		}
	}
	
	public void sendMail(User user) {
		Set<Job> jobs = searchJobs(user.getJobRolePreferences());
		try {
            // Creating a simple mail message
//            SimpleMailMessage mailMessage
//                = new SimpleMailMessage();
//            
//            // Setting up necessary details
//            mailMessage.setFrom("themovieticketbooking@gmail.com");
//            mailMessage.setTo(user.getEmail());
//            String jobCards="";
//            for(Job job : jobs) {
//            	jobCards += "<a href="+ job.getUrl() +"><div class=\"card\" style=\"box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);transition: 0.3s;width: 40%;border-radius: 5px;\"><div class=\"container\" style=\"padding: 2px 16px;\"><h4><b>"+ job.getCompanyName() + "</b></h4><p>" + job.getJobTitle() + "</p></div></div></a>";
//            }
//            mailMessage.setText(jobCards);
//            String jobRole = String.join(",", user.getJobRolePreferences());
//            mailMessage.setSubject("Recommended Jobs for " + jobRole);
            
            
            MimeMessage message = javaMailSender.createMimeMessage();
			String jobRole = String.join(",", user.getJobRolePreferences());
            message.setSubject("Recommended Jobs for " + jobRole);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("themovieticketbooking@gmail.com");
            helper.setTo(user.getEmail());
            String jobCards="";
            for(Job job : jobs) {
            	String jobLocations = String.join(",", job.getLocations());
            	jobCards += "<a href="+ job.getUrl() +"><div class=\"card\" style=\"box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);transition: 0.3s;width: 40%;border-radius: 5px;\"><img src=\"cid:identifier1234\" alt=\"Avatar\" style=\"width:100%;border-radius: 5px 5px 0 0;\"><div class=\"container\" style=\"padding: 2px 16px;\"><h4><b>Company Name: "+ job.getCompanyName() + "</b></h4><p>Role: " + job.getJobTitle() + "</p><b><p>Locations: "+ jobLocations +"</p></div></div></a>";
            }
            helper.setText(jobCards, true);
            FileSystemResource res = new FileSystemResource(new File("C:\\Users\\USER\\Downloads\\JobRecommendation\\JobRecommendation\\src\\main\\resources\\static\\RecommendLogo.png"));
            helper.addInline("identifier1234", res);
            // Sending the mail
            javaMailSender.send(message);
            
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
             System.out.println("Error while Sending Mail");
        }
	}
}
