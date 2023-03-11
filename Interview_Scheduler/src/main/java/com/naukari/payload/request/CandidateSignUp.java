package com.naukari.payload.request;

import com.naukari.models.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateSignUp {

	private String name;

	private String resumeLink;

	private String username;

	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;
	
}
