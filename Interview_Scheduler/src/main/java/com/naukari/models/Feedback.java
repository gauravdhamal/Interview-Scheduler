package com.naukari.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

	private Integer id;

	private Integer candidateId;

	private String message;

	private String comment;

}
