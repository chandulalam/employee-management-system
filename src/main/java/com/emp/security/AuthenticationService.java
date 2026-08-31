package com.emp.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.emp.dto.LoginRequestDTO;

@Service
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;

	public AuthenticationService(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	public void authenticate(LoginRequestDTO loginRequestDTO) {
		UsernamePasswordAuthenticationToken userToken=
				new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
	
		authenticationManager.authenticate(userToken);
	}
}
