package com.emp.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

class JwtServiceTest {

	private JwtService jwtService;

	private static final String SECRET = "replace-this-with-a-long-random-secret-value-at-least-32-bytes";

	@BeforeEach
	void setUp() {
		jwtService = new JwtService();
		ReflectionTestUtils.setField(jwtService, "secret", SECRET);
		ReflectionTestUtils.setField(jwtService, "expiration", 3600000L);
	}

	@Test
	void generateToken_shouldReturnNonEmptyToken() {
		UserDetails userDetails = User.builder()
				.username("testuser")
				.password("password")
				.roles("EMPLOYEE")
				.build();

		String token = jwtService.generateToken(userDetails);

		assertNotNull(token);
		assertFalse(token.isBlank());
	}

	@Test
	void extractUsername_shouldReturnUsernameFromToken() {
		UserDetails userDetails = User.builder()
				.username("testuser")
				.password("password")
				.roles("EMPLOYEE")
				.build();

		String token = jwtService.generateToken(userDetails);

		assertEquals("testuser", jwtService.extractUsername(token));
	}

	@Test
	void isTokenValid_shouldReturnTrue_whenUsernameMatches() {
		UserDetails userDetails = User.builder()
				.username("testuser")
				.password("password")
				.roles("EMPLOYEE")
				.build();

		String token = jwtService.generateToken(userDetails);

		assertTrue(jwtService.isTokenValid(token, userDetails));
	}

	@Test
	void isTokenValid_shouldReturnFalse_whenUsernameDoesNotMatch() {
		UserDetails tokenOwner = User.builder()
				.username("testuser")
				.password("password")
				.roles("EMPLOYEE")
				.build();
		UserDetails otherUser = User.builder()
				.username("otheruser")
				.password("password")
				.roles("EMPLOYEE")
				.build();

		String token = jwtService.generateToken(tokenOwner);

		assertFalse(jwtService.isTokenValid(token, otherUser));
	}
}
