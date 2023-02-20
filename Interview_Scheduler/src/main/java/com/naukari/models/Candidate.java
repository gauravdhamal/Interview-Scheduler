package com.naukari.models;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Candidate {

	private Integer id;

	private String name;

	private String resumeLink;

	@OneToMany
	private Interviewer interviewer;

	@Embedded
	private Feedback feedback;

}
