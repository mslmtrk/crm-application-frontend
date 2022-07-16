package com.spring.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignupRequest {

	@NotEmpty(message="Username is required.")
	@Size(max=30, message="Username must not exceed 30 characters.")
	@Pattern(regexp = "^[a-zA-Z0-9]{4,}$", message = "Username must consists of alphanumeric characters and must be at least 4 characters long.")
	private String username;
	
	@NotEmpty(message="Password is required")
	@Size(max=30, message="Password must not exceed 70 characters.")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must contain at least one number, one uppercase and lowercase letter and must be at least 8 characters long.")
	private String password;
	
	public SignupRequest() {
		
	}

	public SignupRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
