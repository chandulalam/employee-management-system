package com.emp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emp.model.User;
import com.emp.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	public User getUserById(int id) {
		return userRepository.findById(id).orElse(null);
	}
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	public User updateUser(int id ,User user) {
		User existingUser = userRepository.findById(id).orElse(null);
		existingUser.setUsername(user.getUsername());
		existingUser.setRole(user.getRole());
		existingUser.setPassword(user.getPassword());
		existingUser.setEmployee(user.getEmployee());
		
		return userRepository.save(existingUser);
	}
	
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}
}
