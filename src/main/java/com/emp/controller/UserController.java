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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;

	UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public UserResponseDTO saveUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
		return userService.saveUser(userRequestDTO);
	}
	
	@GetMapping("/get/{id}")
	public UserResponseDTO getUser(@PathVariable int id) {
		return userService.getUserById(id);
	}
	
	@GetMapping("/all")
	public List<UserResponseDTO> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PutMapping("/update/{id}")
	public UserResponseDTO updateUser(@PathVariable int id,@Valid @RequestBody UserRequestDTO userRequestDTO) {
		return userService.updateUser(id, userRequestDTO);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable int id) {
		userService.deleteUser(id);
	}
	
	@GetMapping("/profile")
	public UserResponseDTO getMyProfile() {
	    return userService.getMyProfile();
	}
	
	@PutMapping("/profile")
	public UserResponseDTO updateMyProfile(
	        @Valid @RequestBody UserRequestDTO userRequestDTO) {

	    return userService.updateMyProfile(userRequestDTO);
	}
	
	
}
