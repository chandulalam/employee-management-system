package com.emp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.emp.dto.UserRequestDTO;
import com.emp.dto.UserResponseDTO;
import com.emp.exception.EmployeeNotFoundException;
import com.emp.exception.UserNotFoundException;
import com.emp.mapper.UserMapper;
import com.emp.model.Employee;
import com.emp.model.Role;
import com.emp.model.User;
import com.emp.repository.EmployeeRepository;
import com.emp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMapper userMapper;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setUpSecurityContext() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("testuser", "password"));
	}

	@AfterEach
	void clearSecurityContext() {
		SecurityContextHolder.clearContext();
	}

	@Test
	void saveUser_shouldReturnSavedUser() {
		UserRequestDTO request = new UserRequestDTO("john", "password123", 1);
		User user = new User();
		user.setUsername("john");
		Employee employee = new Employee();
		employee.setId(1);
		User savedUser = new User(1, "john", "encodedPassword", Role.EMPLOYEE, employee);
		UserResponseDTO response = new UserResponseDTO(1, "john", Role.EMPLOYEE, 1);

		when(userMapper.userDtoToUser(request)).thenReturn(user);
		when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
		when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
		when(userRepository.save(user)).thenReturn(savedUser);
		when(userMapper.userToUserResponse(savedUser)).thenReturn(response);

		UserResponseDTO result = userService.saveUser(request);

		assertEquals(Role.EMPLOYEE, user.getRole());
		assertEquals(employee, user.getEmployee());
		assertEquals(response, result);
		verify(passwordEncoder).encode("password123");
	}

	@Test
	void saveUser_shouldThrowException_whenEmployeeNotFound() {
		UserRequestDTO request = new UserRequestDTO("john", "password123", 99);
		User user = new User();

		when(userMapper.userDtoToUser(request)).thenReturn(user);
		when(employeeRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(EmployeeNotFoundException.class, () -> userService.saveUser(request));
		verify(userRepository, never()).save(any());
	}

	@Test
	void getUserById_shouldReturnUser_whenFound() {
		Employee employee = new Employee();
		employee.setId(1);
		User user = new User(1, "john", "encodedPassword", Role.EMPLOYEE, employee);
		UserResponseDTO response = new UserResponseDTO(1, "john", Role.EMPLOYEE, 1);

		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userMapper.userToUserResponse(user)).thenReturn(response);

		UserResponseDTO result = userService.getUserById(1);

		assertEquals(response, result);
	}

	@Test
	void getUserById_shouldThrowException_whenNotFound() {
		when(userRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.getUserById(99));
	}

	@Test
	void getAllUsers_shouldReturnAllUsers() {
		Employee employee = new Employee();
		employee.setId(1);
		User user1 = new User(1, "john", "pass", Role.EMPLOYEE, employee);
		User user2 = new User(2, "jane", "pass", Role.ADMIN, employee);
		UserResponseDTO response1 = new UserResponseDTO(1, "john", Role.EMPLOYEE, 1);
		UserResponseDTO response2 = new UserResponseDTO(2, "jane", Role.ADMIN, 1);

		when(userRepository.findAll()).thenReturn(List.of(user1, user2));
		when(userMapper.userToUserResponse(user1)).thenReturn(response1);
		when(userMapper.userToUserResponse(user2)).thenReturn(response2);

		List<UserResponseDTO> result = userService.getAllUsers();

		assertEquals(2, result.size());
		assertEquals(response1, result.get(0));
		assertEquals(response2, result.get(1));
	}

	@Test
	void updateUser_shouldReturnUpdatedUser_whenFound() {
		UserRequestDTO request = new UserRequestDTO("updatedUser", "newPassword", 2);
		Employee employee = new Employee();
		employee.setId(2);
		User existingUser = new User(1, "john", "oldPassword", Role.EMPLOYEE, new Employee());
		UserResponseDTO response = new UserResponseDTO(1, "updatedUser", Role.EMPLOYEE, 2);

		when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
		when(employeeRepository.findById(2)).thenReturn(Optional.of(employee));
		when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
		when(userRepository.save(existingUser)).thenReturn(existingUser);
		when(userMapper.userToUserResponse(existingUser)).thenReturn(response);

		UserResponseDTO result = userService.updateUser(1, request);

		assertEquals("updatedUser", existingUser.getUsername());
		assertEquals(employee, existingUser.getEmployee());
		assertEquals(response, result);
	}

	@Test
	void updateUser_shouldThrowException_whenUserNotFound() {
		UserRequestDTO request = new UserRequestDTO("updatedUser", "newPassword", 1);

		when(userRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.updateUser(99, request));
	}

	@Test
	void updateUser_shouldThrowException_whenEmployeeNotFound() {
		UserRequestDTO request = new UserRequestDTO("updatedUser", "newPassword", 99);
		User existingUser = new User(1, "john", "oldPassword", Role.EMPLOYEE, new Employee());

		when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
		when(employeeRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(EmployeeNotFoundException.class, () -> userService.updateUser(1, request));
	}

	@Test
	void deleteUser_shouldDeleteUser_whenFound() {
		User user = new User(1, "john", "pass", Role.EMPLOYEE, new Employee());

		when(userRepository.findById(1)).thenReturn(Optional.of(user));

		userService.deleteUser(1);

		verify(userRepository).delete(user);
	}

	@Test
	void deleteUser_shouldThrowException_whenNotFound() {
		when(userRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.deleteUser(99));
		verify(userRepository, never()).delete(any());
	}

	@Test
	void getMyProfile_shouldReturnCurrentUserProfile() {
		Employee employee = new Employee();
		employee.setId(1);
		User user = new User(1, "testuser", "pass", Role.EMPLOYEE, employee);
		UserResponseDTO response = new UserResponseDTO(1, "testuser", Role.EMPLOYEE, 1);

		when(userRepository.findUserByUsername("testuser")).thenReturn(user);
		when(userMapper.userToUserResponse(user)).thenReturn(response);

		UserResponseDTO result = userService.getMyProfile();

		assertEquals(response, result);
	}

	@Test
	void getMyProfile_shouldThrowException_whenUserNotFound() {
		when(userRepository.findUserByUsername("testuser")).thenReturn(null);

		assertThrows(UserNotFoundException.class, () -> userService.getMyProfile());
	}

	@Test
	void updateMyProfile_shouldReturnUpdatedProfile() {
		UserRequestDTO request = new UserRequestDTO("newUsername", "newPassword", 1);
		User existingUser = new User(1, "testuser", "oldPassword", Role.EMPLOYEE, new Employee());
		UserResponseDTO response = new UserResponseDTO(1, "newUsername", Role.EMPLOYEE, 1);

		when(userRepository.findUserByUsername("testuser")).thenReturn(existingUser);
		when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
		when(userRepository.save(existingUser)).thenReturn(existingUser);
		when(userMapper.userToUserResponse(existingUser)).thenReturn(response);

		UserResponseDTO result = userService.updateMyProfile(request);

		assertEquals("newUsername", existingUser.getUsername());
		assertEquals(response, result);
	}

	@Test
	void updateMyProfile_shouldThrowException_whenUserNotFound() {
		UserRequestDTO request = new UserRequestDTO("newUsername", "newPassword", 1);

		when(userRepository.findUserByUsername("testuser")).thenReturn(null);

		assertThrows(UserNotFoundException.class, () -> userService.updateMyProfile(request));
	}
}
