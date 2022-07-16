package com.spring.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.spring.entity.SignupRequest;
import com.spring.entity.Customer;
import com.spring.entity.LoginRequest;

public interface EntityService {
	
	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int customerId);

	public void deleteCustomer(int customerId);
	
	public ResponseEntity<?> login(LoginRequest loginRequest);
	
	public ResponseEntity<?> signup(SignupRequest signupRequest);
}
