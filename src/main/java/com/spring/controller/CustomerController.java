package com.spring.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.entity.SignupRequest;
import com.spring.entity.Customer;
import com.spring.entity.LoginRequest;
import com.spring.service.EntityService;

@Controller
public class CustomerController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	private EntityService service;
	
	@GetMapping("/")
	public String listCustomers(Model model) {
		
		List<Customer> customers = service.getCustomers();
		
		model.addAttribute("customers", customers);
		
		return "list-customers";
	}
	
	@GetMapping("/customerForm")
	public String customerForm(Model model) {
		
		if(session.getAttribute("token") == null || (new Date()).after((Date)session.getAttribute("expiryDate")) )
			return "redirect:/login";
		
		Customer theCustomer = new Customer();
		
		model.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		
		if(session.getAttribute("token") == null || (new Date()).after((Date)session.getAttribute("expiryDate")) )
			return "redirect:/login";
		
		service.saveCustomer(theCustomer);
		
		return "redirect:/";
	}
	
	@GetMapping("/updateCustomer")
	public String updateCustomer(@RequestParam(name="customerId") int customerId, Model model) {
		
		if(session.getAttribute("token") == null || (new Date()).after((Date)session.getAttribute("expiryDate")) )
			return "redirect:/login";
			
		Customer theCustomer = service.getCustomer(customerId);
		
		model.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam(name="customerId") int customerId) {
		
		if(session.getAttribute("token") == null || (new Date()).after((Date)session.getAttribute("expiryDate")) )
			return "redirect:/login";
		
		service.deleteCustomer(customerId);
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		
		LoginRequest loginRequest = new LoginRequest();
		
		model.addAttribute("loginRequest", loginRequest);
		
		return "authentication/login";
	}
	
	@GetMapping("/signup")
	public String register(Model model) {
		
		SignupRequest signupRequest = new SignupRequest();
		
		model.addAttribute("signupRequest", signupRequest);
		
		return "authentication/register";
	}
	
}
