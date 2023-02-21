package com.naukari.services;

import com.naukari.exception.RecordNotFoundException;
import com.naukari.models.Candidate;
import com.naukari.models.Interviewer;

public interface CandidateService {

	public Candidate createCandidate(Candidate candidate);

	public Candidate updateCandidateProfile(Integer candidateId, Candidate candidate) throws RecordNotFoundException;

	public Candidate getCandidate(Integer candidateId) throws RecordNotFoundException;

	public String removeCandidate(Integer candidateId) throws RecordNotFoundException;

	public Interviewer getInterviewer(Integer candidateId) throws RecordNotFoundException;

}
