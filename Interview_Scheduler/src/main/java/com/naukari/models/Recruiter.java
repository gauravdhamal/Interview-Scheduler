package com.naukari.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Recruiter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer recruiterId;

	private String name;

	private String mobileNo;

	@Column(unique = true)
	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@JsonIgnore
	@OneToMany(mappedBy = "recruiter")
	private List<Interviewer> interviewers = new ArrayList<>();

	@JsonIgnore
	@OneToMany
	private List<Candidate> candidates = new ArrayList<>();

}
