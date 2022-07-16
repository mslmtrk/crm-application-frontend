package com.spring.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.entity.SignupRequest;
import com.spring.entity.JwtResponse;
import com.spring.entity.LoginRequest;
import com.spring.service.EntityService;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private EntityService service;
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginRequest") LoginRequest loginRequest, BindingResult result, HttpSession session, Model model) {
		
		if (result.hasErrors()) {
            return "authentication/login";
        }
		
		ResponseEntity<?> response = service.login(loginRequest);
		
		if(response.getStatusCodeValue() == 400) {
			
			model.addAttribute("message", "Username or password is incorrect!");
			
			return "authentication/login";
		}
		
		JwtResponse jwt = (JwtResponse) response.getBody();
		
		session.setMaxInactiveInterval(3600);//60 minutes
		
		session.setAttribute("token", jwt.getToken());
		session.setAttribute("username", jwt.getUsername());
		session.setAttribute("expiryDate", jwt.getExpiryDate());
		
		return "redirect:/";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid @ModelAttribute("signupRequest") SignupRequest signupRequest, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
            return "authentication/register";
        }
		
		ResponseEntity<?> response = service.signup(signupRequest);
		
		if(response.getStatusCodeValue() == 400) {
			
			model.addAttribute("message", "This username is already taken!");
			
			return "authentication/register";
		}
		
		return "redirect:/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/";
	}
}
