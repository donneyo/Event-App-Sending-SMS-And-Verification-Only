package com.customerApp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.customerApp.entity.Attendees;
import com.customerApp.service.AttendeesService;


@Controller
public class AttendeeController {
	
		@Autowired
		AttendeesService attendeesService;
	
	
	//method to save attendees
		@PostMapping("/attendee/save")
		public String saveAttendee(Attendees attendee, RedirectAttributes redirectAttributes)
				throws IOException {

			attendeesService.saveAttendee(attendee);
			 
				
				redirectAttributes.addFlashAttribute("message", "The invite has been sent successfully.");

				return "customers";

		}

		
}
