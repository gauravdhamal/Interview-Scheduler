package com.naukari.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naukari.exception.RecordNotFoundException;
import com.naukari.models.Candidate;
import com.naukari.models.Interviewer;
import com.naukari.models.Recruiter;
import com.naukari.repositories.CandidateRepository;
import com.naukari.repositories.InterviewerRepository;
import com.naukari.repositories.RecruiterRepository;

@Service
public class RecruiterServiceImpl implements RecruiterService {

	@Autowired
	private RecruiterRepository recruiterRepository;

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private InterviewerRepository interviewerRepository;

	@Override
	public Recruiter createRecruiter(Recruiter recruiter) {
		return recruiterRepository.save(recruiter);
	}

	@Override
	public Recruiter updateRecruiter(Integer recruiterId, Recruiter recruiter) throws RecordNotFoundException {
		Recruiter oldRecruiter = recruiterRepository.findById(recruiterId)
				.orElseThrow(() -> new RecordNotFoundException("Recruiter not found with Id : " + recruiterId));
		if (recruiter.getName() != null) {
			oldRecruiter.setName(recruiter.getName());
		}
		if (recruiter.getMobileNo() != null) {
			oldRecruiter.setMobileNo(recruiter.getMobileNo());
		}
		return recruiterRepository.save(oldRecruiter);
	}

	@Override
	public Recruiter getRecruiter(Integer recruiterId) throws RecordNotFoundException {
		return recruiterRepository.findById(recruiterId)
				.orElseThrow(() -> new RecordNotFoundException("Recruiter not found with Id : " + recruiterId));
	}

	@Override
	public String deleteRecruiter(Integer recruiterId) throws RecordNotFoundException {
		Recruiter oldRecruiter = recruiterRepository.findById(recruiterId)
				.orElseThrow(() -> new RecordNotFoundException("Recruiter not found with Id : " + recruiterId));
		recruiterRepository.delete(oldRecruiter);
		return "Recruiter with Id : " + recruiterId + " deleted from database.";
	}

	@Override
	public List<Interviewer> getAllInterviewers(Integer recruiterId) throws RecordNotFoundException {
		Recruiter oldRecruiter = recruiterRepository.findById(recruiterId)
				.orElseThrow(() -> new RecordNotFoundException("Recruiter not found with Id : " + recruiterId));
		if (oldRecruiter.getInterviewers().isEmpty())
			throw new RecordNotFoundException("No any interviewer present in the list.");
		else
			return oldRecruiter.getInterviewers();
	}

	@Override
	public List<Candidate> getAllCandidates(Integer recruiterId) throws RecordNotFoundException {
		Recruiter oldRecruiter = recruiterRepository.findById(recruiterId)
				.orElseThrow(() -> new RecordNotFoundException("Recruiter not found with Id : " + recruiterId));
		if (oldRecruiter.getCandidates().isEmpty()) {
			throw new RecordNotFoundException("No any candidate present in the list.");
		} else
			return oldRecruiter.getCandidates();
	}

	@Override
	public String scheduleInterview(Integer recruiterId, Integer candidateId, Integer interviewerId)
			throws RecordNotFoundException {
		Recruiter oldRecruiter = recruiterRepository.findById(recruiterId)
				.orElseThrow(() -> new RecordNotFoundException("Recruiter not found with Id : " + recruiterId));
		Candidate candidate = candidateRepository.findById(candidateId)
				.orElseThrow(() -> new RecordNotFoundException("Candidate not found with Id : " + candidateId));
		Interviewer interviewer = interviewerRepository.findById(interviewerId)
				.orElseThrow(() -> new RecordNotFoundException("Interviewer not found with Id : " + interviewerId));
		if (candidate.getInterviewer() == null) {
			candidate.setInterviewer(interviewer);
			interviewer.getCandidates().add(candidate);
			if (interviewer.getRecruiter() == null)
				interviewer.setRecruiter(oldRecruiter);
			oldRecruiter.getCandidates().add(candidate);
			oldRecruiter.getInterviewers().add(interviewer);
			candidateRepository.save(candidate);
			interviewerRepository.save(interviewer);
			recruiterRepository.save(oldRecruiter);
		} else {
			throw new RecordNotFoundException("Candidate Interview already shceduled with another Interviewer.");
		}
		return null;
	}

	@Override
	public List<Recruiter> getAllRecruiters() throws RecordNotFoundException {
		return recruiterRepository.findAll();
	}
}
