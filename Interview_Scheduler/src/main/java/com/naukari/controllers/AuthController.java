package com.naukari.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import com.naukari.exception.RecordNotFoundException;
import com.naukari.models.Candidate;
import com.naukari.models.Interviewer;
import com.naukari.models.Recruiter;
import com.naukari.models.User;
import com.naukari.payload.request.CandidateSignUp;
import com.naukari.payload.request.InterviewerSignUp;
import com.naukari.payload.request.LoginRequest;
import com.naukari.payload.request.RecruiterSignUp;
import com.naukari.payload.response.MessageResponse;
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
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
//		System.out.println("1. Before exception");
		User user = (User) authentication.getPrincipal();
//		System.out.println("2. After exception user : "+user);
		ResponseCookie responseCookie = jwtUtils.generateResponseCookie(user);
//		System.out.println("3. After cookie generation responseCookie : "+responseCookie);
		UserInfoResponse userInfoResponse = new UserInfoResponse(user.getName(), user.getUsername(), user.getRole());
//		System.out.println("4. Returning response userInfoResponse : "+userInfoResponse);
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(userInfoResponse);
	}

	@PostMapping("/signIn/recruiter")
	public ResponseEntity<String> createRecruiter(@RequestBody RecruiterSignUp recruiterSignUp)
			throws RecordNotFoundException {
		recruiterSignUp.setPassword(passwordEncoder.encode(recruiterSignUp.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		User realUser = modelMapper.map(recruiterSignUp, User.class);
		Recruiter recruiter = modelMapper.map(recruiterSignUp, Recruiter.class);
		Optional<User> user = userRepository.findByUsername(recruiterSignUp.getUsername());
		if (user.isPresent())
			throw new RecordNotFoundException(
					"Recruiter already present in database with username : " + recruiterSignUp.getUsername());
		userRepository.save(realUser);
		recruiterRepository.save(recruiter);
		return new ResponseEntity<String>("Recruiter saved.", HttpStatus.OK);
	}

	@PostMapping("/signIn/interviewer")
	public ResponseEntity<String> createInterviewer(@RequestBody InterviewerSignUp interviewerSignUp)
			throws RecordNotFoundException {
		interviewerSignUp.setPassword(passwordEncoder.encode(interviewerSignUp.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		User realUser = modelMapper.map(interviewerSignUp, User.class);
		Interviewer interviewer = modelMapper.map(interviewerSignUp, Interviewer.class);
		Optional<User> user = userRepository.findByUsername(interviewerSignUp.getUsername());
		if (user.isPresent())
			throw new RecordNotFoundException(
					"Interviewer already present in database with username : " + interviewerSignUp.getUsername());
		userRepository.save(realUser);
		interviewerRepository.save(interviewer);
		return new ResponseEntity<String>("Interviewer saved.", HttpStatus.OK);
	}

	@PostMapping("/signIn/candidate")
	public ResponseEntity<String> createCandidate(@RequestBody CandidateSignUp candidateSignUp)
			throws RecordNotFoundException {
		candidateSignUp.setPassword(passwordEncoder.encode(candidateSignUp.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		User realUser = modelMapper.map(candidateSignUp, User.class);
		Candidate candidate = modelMapper.map(candidateSignUp, Candidate.class);
		Optional<User> user = userRepository.findByUsername(candidateSignUp.getUsername());
		if (user.isPresent())
			throw new RecordNotFoundException(
					"Candidate already present in database with username : " + candidateSignUp.getUsername());
		userRepository.save(realUser);
		candidateRepository.save(candidate);
		return new ResponseEntity<String>("Candidate saved.", HttpStatus.OK);
	}

	@PostMapping("/signOut")
	public ResponseEntity<?> logOutUser() {
		ResponseCookie responseCookie = jwtUtils.getCleanCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString())
				.body(new MessageResponse("Signout successful. Visit again...!!"));
	}

}
