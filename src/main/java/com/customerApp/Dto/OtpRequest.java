package com.customerApp.Dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {
	private Integer id;
	private String username;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastname;
}
