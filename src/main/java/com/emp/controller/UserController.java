package com.emp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.dto.UserRequestDTO;
import com.emp.dto.UserResponseDTO;
import com.emp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;

	UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(
			summary = "Register employee",
			description = "Public endpoint for employee registration"
	)
	@PostMapping("/register")
	public UserResponseDTO saveUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
		return userService.saveUser(userRequestDTO);
	}
	
	@Operation(
			summary = "Get user by ID",
			description = "Accessible by authenticated users"
	)
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/get/{id}")
	public UserResponseDTO getUser(@PathVariable int id) {
		return userService.getUserById(id);
	}
	
	@Operation(
			summary = "Get all users",
			description = "Accessible only by ADMIN"
	)
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/all")
	public List<UserResponseDTO> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@Operation(
			summary = "Update user by ID",
			description = "Accessible only by ADMIN"
	)
	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/update/{id}")
	public UserResponseDTO updateUser(@PathVariable int id,@Valid @RequestBody UserRequestDTO userRequestDTO) {
		return userService.updateUser(id, userRequestDTO);
	}
	
	@Operation(
			summary = "Delete user by ID",
			description = "Accessible only by ADMIN"
	)
	@SecurityRequirement(name = "bearerAuth")
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable int id) {
		userService.deleteUser(id);
	}
	
	@Operation(
			summary = "Get my profile",
			description = "Accessible by authenticated users"
	)
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/profile")
	public UserResponseDTO getMyProfile() {
	    return userService.getMyProfile();
	}
	
	@Operation(
			summary = "Update my profile",
			description = "Accessible by authenticated users"
	)
	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/profile")
	public UserResponseDTO updateMyProfile(
	        @Valid @RequestBody UserRequestDTO userRequestDTO) {

	    return userService.updateMyProfile(userRequestDTO);
	}
}
