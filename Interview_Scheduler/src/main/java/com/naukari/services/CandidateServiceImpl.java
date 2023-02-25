package com.naukari.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naukari.exception.RecordNotFoundException;
import com.naukari.models.Candidate;
import com.naukari.models.Interviewer;
import com.naukari.repositories.CandidateRepository;

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	private CandidateRepository candidateRepository;

	@Override
	public Candidate createCandidate(Candidate candidate) {
		System.out.println("Candidate saved to database success.");
		return candidateRepository.save(candidate);
	}

	@Override
	public Candidate updateCandidateProfile(Integer candidateId, Candidate candidate) throws RecordNotFoundException {
		Candidate oldCandidate = candidateRepository.findById(candidateId)
				.orElseThrow(() -> new RecordNotFoundException("Candidate not found with Id : " + candidateId));
		if (candidate.getName() != null) {
			oldCandidate.setName(candidate.getName());
		}
		if (candidate.getResumeLink() != null) {
			oldCandidate.setResumeLink(candidate.getResumeLink());
		}
		System.out.println("Candidate details updated success.");
		return candidateRepository.save(oldCandidate);
	}

	@Override
	public Candidate getCandidate(Integer candidateId) throws RecordNotFoundException {
		return candidateRepository.findById(candidateId)
				.orElseThrow(() -> new RecordNotFoundException("Candidate not found with Id : " + candidateId));
	}

	@Override
	public String removeCandidate(Integer candidateId) throws RecordNotFoundException {
		Candidate candidate = candidateRepository.findById(candidateId)
				.orElseThrow(() -> new RecordNotFoundException("Candidate not found with Id : " + candidateId));
		candidateRepository.delete(candidate);
		return "Candidate with Id : " + candidateId + " : removed from database.";
	}

	@Override
	public Interviewer getInterviewer(Integer candidateId) throws RecordNotFoundException {
		Candidate candidate = candidateRepository.findById(candidateId)
				.orElseThrow(() -> new RecordNotFoundException("Candidate not found with Id : " + candidateId));
		Interviewer interviewer = candidate.getInterviewer();
		System.out.println("Interviewer found." + interviewer);
		return interviewer;
	}

	@Override
	public List<Candidate> getAllCandidates() throws RecordNotFoundException {
		return candidateRepository.findAll();
	}

}
