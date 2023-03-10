package com.naukari.services;

import java.util.List;

import com.naukari.exception.RecordNotFoundException;
import com.naukari.models.Candidate;
import com.naukari.models.Feedback;
import com.naukari.models.Interviewer;

public interface CandidateService {

	public Candidate createCandidate(Candidate candidate);

	public Candidate updateCandidateProfile(Integer candidateId, Candidate candidate) throws RecordNotFoundException;

	public Candidate getCandidate(Integer candidateId) throws RecordNotFoundException;

	public String removeCandidate(Integer candidateId) throws RecordNotFoundException;

	public Interviewer getInterviewer(Integer candidateId) throws RecordNotFoundException;

	public List<Candidate> getAllCandidates() throws RecordNotFoundException;

	public Feedback getFeedback(Integer candidateId) throws RecordNotFoundException;

}
