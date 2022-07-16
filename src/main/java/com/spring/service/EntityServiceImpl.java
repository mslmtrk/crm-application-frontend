package com.spring.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.config.RestTemplateErrorHandler;
import com.spring.entity.SignupRequest;
import com.spring.entity.Customer;
import com.spring.entity.JwtResponse;
import com.spring.entity.LoginRequest;
import com.spring.entity.MessageResponse;

@Service
public class EntityServiceImpl implements EntityService {
	
	
	private RestTemplate restTemplate;
	
	@Autowired
	public EntityServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.restTemplate.setErrorHandler(new RestTemplateErrorHandler());
	}
	
	@Autowired
	private HttpSession session;

	@Value("${rest.customers.url}")
	private String restCustomerUrl;
	
	@Value("${rest.singin.url}")
	private String restSigninUrl;
	
	@Value("${rest.signup.url}")
	private String restSignupUrl;

	private HttpHeaders getHeaders() {
	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + session.getAttribute("token"));

		return headers;
	}
	
	@Override
	public List<Customer> getCustomers() {
		
		ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(restCustomerUrl, 
				HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<Customer>>() {});
		
		List<Customer> customers = responseEntity.getBody();

		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		
		int customerId = theCustomer.getId();
		
		HttpEntity<?> requestEntity = new HttpEntity<>(theCustomer, getHeaders());
		
		if (customerId == 0) {
			
			restTemplate.postForEntity(restCustomerUrl, requestEntity, Customer.class);
		}
		else {
			
			restTemplate.put(restCustomerUrl, requestEntity);
		}
	}

	@Override
	public Customer getCustomer(int customerId) {
		
		HttpEntity<?> requestEntity = new HttpEntity<>(getHeaders());
		
		ResponseEntity<Customer> theCustomer = restTemplate.exchange(restCustomerUrl + "/" + customerId, HttpMethod.GET, requestEntity, Customer.class);
		
		return theCustomer.getBody();
	}

	@Override
	public void deleteCustomer(int customerId) {
		
		HttpEntity<?> requestEntity = new HttpEntity<>(getHeaders());

		restTemplate.exchange(restCustomerUrl + "/" + customerId, HttpMethod.DELETE, requestEntity, String.class);
	}

	@Override
	public ResponseEntity<?> login(LoginRequest loginRequest) {
		
		ResponseEntity<?> response = restTemplate.postForEntity(restSigninUrl, loginRequest, JwtResponse.class);
		
		return response;
	}

	@Override
	public ResponseEntity<?> signup(SignupRequest signupRequest) {
		
		ResponseEntity<?> response = restTemplate.postForEntity(restSignupUrl, signupRequest, MessageResponse.class);
		
		return response;
	}

}
