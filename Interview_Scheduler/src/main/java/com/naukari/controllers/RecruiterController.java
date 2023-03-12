package com.naukari.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naukari.exception.RecordNotFoundException;
import com.naukari.models.Candidate;
import com.naukari.models.Interviewer;
import com.naukari.models.Recruiter;
import com.naukari.services.RecruiterService;

@RestController
@RequestMapping("/api/recruiter")
public class RecruiterController {

	@Autowired
	private RecruiterService recruiterService;

	@PostMapping("/create")
	public ResponseEntity<Recruiter> createRecruiter(@RequestBody Recruiter recruiter) {
		Recruiter createdRecruiter = recruiterService.createRecruiter(recruiter);
		return new ResponseEntity<Recruiter>(createdRecruiter, HttpStatus.CREATED);
	}

	@PutMapping("/update/{recruiterId}")
	public ResponseEntity<Recruiter> updateRecruiter(@PathVariable("recruiterId") Integer recruitrtId,
			@RequestBody Recruiter recruiter) throws RecordNotFoundException {
		Recruiter updatedRecruiter = recruiterService.updateRecruiter(recruitrtId, recruiter);
		return new ResponseEntity<Recruiter>(updatedRecruiter, HttpStatus.OK);
	}

	@GetMapping("/get/{recruiterId}")
	public ResponseEntity<Recruiter> getRecruiter(@PathVariable("recruiterId") Integer recruiterId)
			throws RecordNotFoundException {
		Recruiter recruiter = recruiterService.getRecruiter(recruiterId);
		return new ResponseEntity<Recruiter>(recruiter, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{recruiterId}")
	public ResponseEntity<String> deleteRecruiter(@PathVariable("recruiterId") Integer recruiterId)
			throws RecordNotFoundException {
		String result = recruiterService.deleteRecruiter(recruiterId);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@GetMapping("/all/interviewers/{recruiterId}")
	public ResponseEntity<List<Interviewer>> getAllInterviewers(@PathVariable("recruiterId") Integer recruiterId)
			throws RecordNotFoundException {
		List<Interviewer> interviewers = recruiterService.getAllInterviewers(recruiterId);
		return new ResponseEntity<List<Interviewer>>(interviewers, HttpStatus.OK);
	}

	@GetMapping("/all/candidates/{recruiterId}")
	public ResponseEntity<List<Candidate>> getAllCandidates(@PathVariable("recruiterId") Integer recruiterId)
			throws RecordNotFoundException {
		List<Candidate> candidates = recruiterService.getAllCandidates(recruiterId);
		return new ResponseEntity<List<Candidate>>(candidates, HttpStatus.OK);
	}

	@PostMapping("/schedule/interview/{recruiterId}/{interviewerId}/{candidateId}")
	public ResponseEntity<String> scheduleInterview(@PathVariable("recruiterId") Integer recruitetId,
			@PathVariable("candidateId") Integer candidateId, @PathVariable("interviewerId") Integer interviewerId,
			String slot) throws RecordNotFoundException {
		String result = recruiterService.scheduleInterview(recruitetId, candidateId, interviewerId, slot);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Recruiter>> getAllRecruiters() throws RecordNotFoundException {
		List<Recruiter> recruiters = recruiterService.getAllRecruiters();
		return new ResponseEntity<List<Recruiter>>(recruiters, HttpStatus.OK);
	}

}