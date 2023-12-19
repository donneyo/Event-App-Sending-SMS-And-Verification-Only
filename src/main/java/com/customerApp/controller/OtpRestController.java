package com.customerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerApp.Dto.OtpRequest;
import com.customerApp.Dto.OtpValidationRequest;
import com.customerApp.twilioSms.SmsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OtpRestController {

	@Autowired
	private SmsService smsService;
	
	@GetMapping("/process")
	public String processSMS() {
		return "SMS sent";
	}
	
//	@PostMapping("/send-otp")
//	public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest) {
//		log.info("inside sendOtp :: "+otpRequest.getUsername());
//		return smsService.sendSMS(otpRequest);
//	}

	//trying this out
	@PostMapping("/otp/send-otp")
	public String sendOtp(OtpRequest otpRequest) {
		log.info("inside sendOtp :: "+otpRequest.getUsername());
		if (smsService.sendSMS(otpRequest)) {
			return "OK";
		}else {
			return "Duplicate";
		}
	
	}
	@PostMapping("/validate-otp")
    public String validateOtp( OtpValidationRequest otpValidationRequest) {
		log.info("inside validateOtp :: "+otpValidationRequest.getUsername()+" "+otpValidationRequest.getOtpNumber());
		return smsService.validateOtp(otpValidationRequest);
    }
	
}

