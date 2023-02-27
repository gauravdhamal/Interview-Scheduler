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
import com.naukari.models.Feedback;
import com.naukari.models.Interviewer;
import com.naukari.services.CandidateService;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

	@Autowired
	private CandidateService candidateService;

	@PostMapping("/create")
	public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
		Candidate createdCandidate = candidateService.createCandidate(candidate);
		return new ResponseEntity<Candidate>(createdCandidate, HttpStatus.CREATED);
	}

	@PutMapping("/update/{candidateId}")
	public ResponseEntity<Candidate> updateCandidateProfile(@PathVariable("candidateId") Integer candidateId,
			@RequestBody Candidate candidate) throws RecordNotFoundException {
		Candidate updatedCandidate = candidateService.updateCandidateProfile(candidateId, candidate);
		return new ResponseEntity<Candidate>(updatedCandidate, HttpStatus.OK);
	}

	@GetMapping("/get/{candidateId}")
	public ResponseEntity<Candidate> getCandidate(@PathVariable("candidateId") Integer candidateId)
			throws RecordNotFoundException {
		Candidate candidate = candidateService.getCandidate(candidateId);
		return new ResponseEntity<Candidate>(candidate, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{candidateId}")
	public ResponseEntity<String> removeCandidate(@PathVariable("candidateId") Integer candidateId)
			throws RecordNotFoundException {
		String result = candidateService.removeCandidate(candidateId);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@GetMapping("/interviewer/{candidateId}")
	public ResponseEntity<Interviewer> getInterviewer(@PathVariable("candidateId") Integer candidateId)
			throws RecordNotFoundException {
		Interviewer interviewer = candidateService.getInterviewer(candidateId);
		return new ResponseEntity<Interviewer>(interviewer, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Candidate>> getAllCandidates() throws RecordNotFoundException {
		List<Candidate> candidates = candidateService.getAllCandidates();
		return new ResponseEntity<List<Candidate>>(candidates, HttpStatus.OK);
	}

	@GetMapping("/feedback/{candidateId}")
	public ResponseEntity<Feedback> getFeedback(@PathVariable("candidateId") Integer candidateId)
			throws RecordNotFoundException {
		Feedback feedback = candidateService.getFeedback(candidateId);
		return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
	}

}
