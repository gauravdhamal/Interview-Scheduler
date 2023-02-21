package com.naukari.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecruiterController {

	@GetMapping("/")
	public String getHome() {
		return "Welcome to home";
	}

}
