package com.naukari.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naukari.exception.RecordNotFoundException;
import com.naukari.models.Candidate;
import com.naukari.models.Feedback;
import com.naukari.models.Interviewer;
import com.naukari.repositories.CandidateRepository;
import com.naukari.repositories.InterviewerRepository;

@Service
public class InterviewerServiceImpl implements InterviewerService {

	@Autowired
	private InterviewerRepository interviewerRepository;

	@Autowired
	private CandidateRepository candidateRepository;

	@Override
	public Interviewer createInterviewer(Interviewer interviewer) {
		System.out.println("Interviewer saved to database.");
		return interviewerRepository.save(interviewer);
	}

	@Override
	public Interviewer updateInterviewver(Integer interviewerId, Interviewer interviewer)
			throws RecordNotFoundException {
		Interviewer oldInterviewer = interviewerRepository.findById(interviewerId)
				.orElseThrow(() -> new RecordNotFoundException("Interviewer not found with Id " + interviewerId));
		if (interviewer.getName() != null) {
			oldInterviewer.setName(interviewer.getName());
		}
		if (interviewer.getMobileNo() != null) {
			oldInterviewer.setMobileNo(interviewer.getMobileNo());
		}
		System.out.println("Interviewer updated.");
		return interviewerRepository.save(oldInterviewer);
	}

	@Override
	public Interviewer getInterviewerById(Integer interviewerId) throws RecordNotFoundException {
		return interviewerRepository.findById(interviewerId)
				.orElseThrow(() -> new RecordNotFoundException("Interviewer not found with Id : " + interviewerId));
	}

	@Override
	public String deleteInterviewerById(Integer interviewerId) throws RecordNotFoundException {
		Interviewer interviewer = interviewerRepository.findById(interviewerId)
				.orElseThrow(() -> new RecordNotFoundException("Interviewer not found with Id : " + interviewerId));
		interviewerRepository.delete(interviewer);
		return "Interviewer with Id " + interviewerId + " removed from database.";
	}

	@Override
	public List<Candidate> getAllCandidates(Integer interviewerId) throws RecordNotFoundException {
		Interviewer interviewer = interviewerRepository.findById(interviewerId)
				.orElseThrow(() -> new RecordNotFoundException("Interviewer not found with Id : " + interviewerId));
		if (!interviewer.getCandidates().isEmpty()) {
			return interviewer.getCandidates();
		} else
			throw new RecordNotFoundException("No any candidate present inside list.");
	}

	@Override
	public String scheduleInterviewWithCandidate(Integer candidateId, Integer interviewerId)
			throws RecordNotFoundException {
		Interviewer interviewer = interviewerRepository.findById(interviewerId)
				.orElseThrow(() -> new RecordNotFoundException("Interviewer not found with Id : " + interviewerId));
		Candidate candidate = candidateRepository.findById(candidateId)
				.orElseThrow(() -> new RecordNotFoundException("Candidate not found with Id : " + candidateId));
		if (candidate.getInterviewer() != null)
			throw new RecordNotFoundException("Candidate already linked to another interviewer.");
		interviewer.getCandidates().add(candidate);
		candidate.setInterviewer(interviewer);
		interviewerRepository.save(interviewer);
		candidateRepository.save(candidate);
		return "Interview scheduled with the candidate having Id : " + candidateId;
	}

	@Override
	public List<Interviewer> getAllInterviewers() throws RecordNotFoundException {
		return interviewerRepository.findAll();
	}

	@Override
	public String giveFeedbackToCandidate(Integer candidateId, Integer interviewerId) throws RecordNotFoundException {
		Interviewer interviewer = interviewerRepository.findById(interviewerId)
				.orElseThrow(() -> new RecordNotFoundException("Interviewer not found with Id : " + interviewerId));
		Candidate candidate = candidateRepository.findById(candidateId)
				.orElseThrow(() -> new RecordNotFoundException("Candidate not found with Id : " + candidateId));
		if (candidate.getInterviewer().getId() != interviewer.getId())
			throw new RecordNotFoundException("Candidate does not belongs to interviewer with Id : " + interviewerId);
		Feedback feedback = new Feedback(candidateId, "Hire", "Need more clarification on projects side");
		if (candidate.getFeedback() == null)
			candidate.setFeedback(feedback);
		else
			throw new RecordNotFoundException("Feedback already given to candidate.");
		candidateRepository.save(candidate);
		return "Feedback added to candidate with Id : " + candidateId;
	}

}
