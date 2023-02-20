package com.naukari.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naukari.models.Interviewer;

@Repository
public interface InterviewerRepository extends JpaRepository<Interviewer, Integer> {

}
