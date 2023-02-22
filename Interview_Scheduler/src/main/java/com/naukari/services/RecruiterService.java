package com.naukari.services;

import java.util.List;

import com.naukari.exception.RecordNotFoundException;
import com.naukari.models.Candidate;
import com.naukari.models.Interviewer;
import com.naukari.models.Recruiter;

public interface RecruiterService {

	public Recruiter createRecruiter(Recruiter recruiter);

	public Recruiter updateRecruiter(Integer recruitrtId, Recruiter recruiter) throws RecordNotFoundException;

	public Recruiter getRecruiter(Integer recruiterId) throws RecordNotFoundException;

	public String deleteRecruiter(Integer recruiterId) throws RecordNotFoundException;

	public List<Interviewer> getAllInterviewers(Integer recruiterId) throws RecordNotFoundException;

	public List<Candidate> getAllCandidates(Integer recruiterId) throws RecordNotFoundException;

	public String scheduleInterview(Integer recruitetId, Integer candidateId, Integer interviewerId)
			throws RecordNotFoundException;

}
