package com.jr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jr.model.Job;
import com.jr.model.User;
import com.jr.service.JobService;
import com.jr.service.UserService;


@CrossOrigin
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JobService jobService;
	
	@PostMapping(path="/searchJobs")
	public ResponseEntity<?> saveUserAndSuggestJobs(@Valid @RequestBody User user, BindingResult br){
		if (br.hasErrors()) {
			List<String> errors = br.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
	        return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
        }
		if(userService.checkUserExists(user.getEmail())) {
			Set<Job> jobs = jobService.searchJobs(user.getJobRolePreferences());
			return new ResponseEntity<Set<Job>>(jobs, HttpStatus.OK);
		}
		else {
			User savedUser = userService.saveUser(user);
			if(savedUser!=null) {
				Set<Job> jobs = jobService.searchJobs(user.getJobRolePreferences());
				return new ResponseEntity<Set<Job>>(jobs, HttpStatus.OK); 
			}
		}
		return new ResponseEntity<String>("Unable to process", HttpStatus.BAD_REQUEST);
	}
}
