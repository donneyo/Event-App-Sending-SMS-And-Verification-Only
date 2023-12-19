package com.customerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customerApp.entity.Attendees;

public interface AttendeeRepo extends JpaRepository<Attendees, Integer> {

	
	
	
}
