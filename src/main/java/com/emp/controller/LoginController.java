package com.emp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.dto.LoginRequestDTO;
import com.emp.security.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class LoginController {
	
	private final AuthenticationService authenticationService;

	public LoginController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/login")
	public String userLogin(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
		return authenticationService.authenticate(loginRequestDTO);
	}
}
