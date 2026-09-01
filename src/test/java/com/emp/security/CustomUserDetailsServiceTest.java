package com.emp.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.emp.model.Employee;
import com.emp.model.Role;
import com.emp.model.User;
import com.emp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	@Test
	void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
		User user = new User(1, "testuser", "encodedPassword", Role.ADMIN, new Employee());

		when(userRepository.findUserByUsername("testuser")).thenReturn(user);

		UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

		assertEquals("testuser", userDetails.getUsername());
		assertEquals("encodedPassword", userDetails.getPassword());
		assertEquals(1, userDetails.getAuthorities().size());
		assertEquals("ROLE_ADMIN", userDetails.getAuthorities().iterator().next().getAuthority());
	}

	@Test
	void loadUserByUsername_shouldThrowException_whenUserNotFound() {
		when(userRepository.findUserByUsername("unknown")).thenReturn(null);

		assertThrows(UsernameNotFoundException.class,
				() -> customUserDetailsService.loadUserByUsername("unknown"));
	}
}
