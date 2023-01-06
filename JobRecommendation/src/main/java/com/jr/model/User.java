package com.jr.model;

import java.util.Set;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	@Size(min=3, message="Username must be minimum 3 characters")
	private String name;
	@NotEmpty(message="Email cannot be empty")
	@Email
	private String email;
	private Set<String> jobRolePreferences;
	@OneToMany(mappedBy="user", fetch = FetchType.EAGER)
	private Set<Job> jobsReceived;
	
	public User() {
		super();
	}

	public User(String name, String email, Set<String> jobRolePreferences, Set<Job> jobsReceived) {
		super();
		this.name = name;
		this.email = email;
		this.jobRolePreferences = jobRolePreferences;
		this.jobsReceived = jobsReceived;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<String> getJobRolePreferences() {
		return jobRolePreferences;
	}

	public void setJobRolePreferences(Set<String> jobRolePreferences) {
		this.jobRolePreferences = jobRolePreferences;
	}

	public Set<Job> getJobsReceived() {
		return jobsReceived;
	}
	public void setJobsReceived(Set<Job> jobsReceived) {
		this.jobsReceived = jobsReceived;
	}
	
}
