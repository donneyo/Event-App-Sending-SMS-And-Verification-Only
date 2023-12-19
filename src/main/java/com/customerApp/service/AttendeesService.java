package com.customerApp.service;

import com.customerApp.entity.Attendees;
import com.customerApp.repository.AttendeeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendeesService {

	@Autowired
	private AttendeeRepo attendeeRepo;
	
	
	public Attendees saveAttendee (Attendees attendee) {
		return attendeeRepo.save(attendee);		
		
		
	}
} 
