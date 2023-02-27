package com.naukari.services;

import java.util.List;

import com.naukari.exception.RecordNotFoundException;
import com.naukari.models.Candidate;
import com.naukari.models.Feedback;
import com.naukari.models.Interviewer;

public interface InterviewerService {

	public Interviewer createInterviewer(Interviewer interviewer);

	public Interviewer updateInterviewver(Integer interviewerId, Interviewer interviewer)
			throws RecordNotFoundException;

	public Interviewer getInterviewerById(Integer interviewerId) throws RecordNotFoundException;

	public String deleteInterviewerById(Integer interviewerId) throws RecordNotFoundException;

	public List<Candidate> getAllCandidates(Integer interviewerId) throws RecordNotFoundException;

	public String scheduleInterviewWithCandidate(Integer candidateId, Integer interviewerId)
			throws RecordNotFoundException;

	public List<Interviewer> getAllInterviewers() throws RecordNotFoundException;

	public String giveFeedbackToCandidate(Integer candidateId, Integer interviewerId, Feedback feedback) throws RecordNotFoundException;

}
