package com.emp.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.emp.dto.LoginRequestDTO;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

	@Mock
	private JwtService jwtService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private Authentication authentication;

	@InjectMocks
	private AuthenticationService authenticationService;

	@Test
	void authenticate_shouldReturnJwtToken_whenCredentialsAreValid() {
		LoginRequestDTO request = new LoginRequestDTO("admin", "password123");
		UserDetails userDetails = User.builder()
				.username("admin")
				.password("password123")
				.roles("ADMIN")
				.build();

		when(authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken("admin", "password123")))
				.thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(userDetails);
		when(jwtService.generateToken(userDetails)).thenReturn("jwt-token");

		String token = authenticationService.authenticate(request);

		assertEquals("jwt-token", token);
		verify(authenticationManager).authenticate(
				new UsernamePasswordAuthenticationToken("admin", "password123"));
		verify(jwtService).generateToken(userDetails);
	}

	@Test
	void authenticate_shouldThrowException_whenCredentialsAreInvalid() {
		LoginRequestDTO request = new LoginRequestDTO("admin", "wrongPassword");

		when(authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken("admin", "wrongPassword")))
				.thenThrow(new BadCredentialsException("Bad credentials"));

		assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(request));
	}
}
