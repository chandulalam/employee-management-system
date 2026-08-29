package com.emp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emp.dto.UserRequestDTO;
import com.emp.dto.UserResponseDTO;
import com.emp.mapper.UserMapper;
import com.emp.model.User;
import com.emp.repository.EmployeeRepository;
import com.emp.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final EmployeeRepository employeeRepository;
	public UserService(UserRepository userRepository,UserMapper userMapper,EmployeeRepository employeeRepository ) {
		this.userRepository=userRepository;
		this.userMapper=userMapper;
		this.employeeRepository=employeeRepository;
	}
	
	public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
		
		User user = userMapper.userDtoToUser(userRequestDTO);
		user.setEmployee(employeeRepository.findById(userRequestDTO.getEmployeeId()).orElse(null));
		User saveUser = userRepository.save(user);
		
		return userMapper.userToUserResponse(saveUser);
	}
	public UserResponseDTO getUserById(int id) {
		
		User user = userRepository.findById(id).orElse(null);
		return userMapper.userToUserResponse(user);
	}
	public List<UserResponseDTO> getAllUsers(){
		
		 List<User> allUsers = userRepository.findAll();
		 
		 return allUsers
				 .stream()
				 .map(userMapper::userToUserResponse)
				 .toList();
	}
	public UserResponseDTO updateUser(int id ,UserRequestDTO userRequestDTO) {
		User existingUser = userRepository.findById(id).orElse(null);
		existingUser.setUsername(userRequestDTO.getUsername());
		existingUser.setRole(userRequestDTO.getRole());
		existingUser.setPassword(userRequestDTO.getPassword());
		existingUser.setEmployee(employeeRepository.findById(userRequestDTO.getEmployeeId()).orElse(null));
		
		userRepository.save(existingUser);
		return userMapper.userToUserResponse(existingUser);
	}
	
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}
}
