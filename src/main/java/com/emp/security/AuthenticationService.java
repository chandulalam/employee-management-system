package com.emp.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.emp.dto.LoginRequestDTO;

@Service
public class AuthenticationService {
	
	private final  JwtService jwtService;

	private final AuthenticationManager authenticationManager;

	public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}
	
	public String authenticate(LoginRequestDTO loginRequestDTO) {
		UsernamePasswordAuthenticationToken userToken=
				new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
	
		Authentication authentication = authenticationManager.authenticate(userToken); 
	
		UserDetails userDetails=(UserDetails)authentication.getPrincipal();
		
		return jwtService.generateToken(userDetails);
	}
}
