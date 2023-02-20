package com.naukari.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

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

	private Integer id;

	private String name;

	private String mobileNo;

	@OneToMany
	private List<Candidate> candidates = new ArrayList<>();

}
