package com.naukari.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Interviewer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String name;

	private String mobileNo;

	private List<LocalDate> dates = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "interviewer")
	private List<Candidate> candidates = new ArrayList<>();

	@JsonIgnore
	@ManyToOne
	private Recruiter recruiter;

//	@Enumerated(EnumType.STRING)
//	private Map<Slot, List<Time>> slotAndTime = new HashMap<>();

}
