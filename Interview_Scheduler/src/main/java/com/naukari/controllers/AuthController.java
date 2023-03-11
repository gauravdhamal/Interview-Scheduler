package com.naukari.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naukari.models.User;
import com.naukari.payload.request.LoginRequest;
import com.naukari.payload.response.UserInfoResponse;
import com.naukari.repositories.CandidateRepository;
import com.naukari.repositories.InterviewerRepository;
import com.naukari.repositories.RecruiterRepository;
import com.naukari.repositories.UserRepository;
import com.naukari.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private InterviewerRepository interviewerRepository;
	
	@Autowired
	private RecruiterRepository recruiterRepository;	

	@PostMapping("/logIn")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getPassword(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = (User) authentication.getPrincipal();
		ResponseCookie responseCookie = jwtUtils.generateResponseCookie(user);
		UserInfoResponse userInfoResponse = new UserInfoResponse(user.getName(), user.getUsername(), user.getRole());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(userInfoResponse);
	}
	
	

}
