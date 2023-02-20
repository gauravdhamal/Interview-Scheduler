package com.naukari.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naukari.models.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

}
