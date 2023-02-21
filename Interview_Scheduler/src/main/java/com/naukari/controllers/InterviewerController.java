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
import com.naukari.services.InterviewerService;

@RestController
@RequestMapping("/interviewer")
public class InterviewerController {

	@Autowired
	private InterviewerService interviewerService;

	@PostMapping("/create")
	public ResponseEntity<Interviewer> createInterviewer(@RequestBody Interviewer interviewer) {
		Interviewer createdInterviewer = interviewerService.createInterviewer(interviewer);
		return new ResponseEntity<Interviewer>(createdInterviewer, HttpStatus.CREATED);
	}

	@PutMapping("/update/{interviewerId}")
	public ResponseEntity<Interviewer> updateInterviewver(@PathVariable("interviewerId") Integer interviewerId,
			@RequestBody Interviewer interviewer) throws RecordNotFoundException {
		Interviewer updatedInterviewer = interviewerService.updateInterviewver(interviewerId, interviewer);
		return new ResponseEntity<Interviewer>(updatedInterviewer, HttpStatus.OK);
	}

	@GetMapping("/get/{interviewerId}")
	public ResponseEntity<Interviewer> getInterviewerById(@PathVariable("interviewerId") Integer interviewerId)
			throws RecordNotFoundException {
		Interviewer interviewer = interviewerService.getInterviewerById(interviewerId);
		return new ResponseEntity<Interviewer>(interviewer, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{interviewerId}")
	public ResponseEntity<String> deleteInterviewerById(@PathVariable("interviewerId") Integer interviewerId)
			throws RecordNotFoundException {
		String result = interviewerService.deleteInterviewerById(interviewerId);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@GetMapping("/all/candidates/{interviewerId}")
	public ResponseEntity<List<Candidate>> getAllCandidates(@PathVariable("interviewerId") Integer interviewerId)
			throws RecordNotFoundException {
		List<Candidate> candidates = interviewerService.getAllCandidates(interviewerId);
		return new ResponseEntity<List<Candidate>>(candidates, HttpStatus.OK);
	}

	@PostMapping("/schedule/{candidateId}/{interviewerId}")
	public ResponseEntity<String> scheduleInterviewWithCandidate(@PathVariable("candidateId") Integer candidateId,
			@PathVariable("interviewerId") Integer interviewerId) throws RecordNotFoundException {
		String result = interviewerService.scheduleInterviewWithCandidate(candidateId, interviewerId);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

}
