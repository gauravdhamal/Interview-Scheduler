package com.naukari.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naukari.models.Recruiter;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Integer> {

}
