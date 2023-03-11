package com.naukari.payload.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.naukari.models.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewerSignUp {

	private String name;

	private String mobileNo;

	private String username;

	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	private List<LocalDate> dates = new ArrayList<>();
	
}
