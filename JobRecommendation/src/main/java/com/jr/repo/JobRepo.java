package com.jr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jr.model.Job;

import jakarta.transaction.Transactional;

@Transactional
public interface JobRepo extends JpaRepository<Job, Integer> {
	
	 @Modifying
	 @Query(
        value = "truncate table Job",
        nativeQuery = true
	)
	void truncateTable();
	List<Job> findTop10ByJobTitleContainingIgnoreCase(String jobKeyword);
	 
}
