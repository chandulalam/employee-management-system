package com.emp.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emp.dto.UserRequestDTO;
import com.emp.dto.UserResponseDTO;
import com.emp.exception.EmployeeNotFoundException;
import com.emp.exception.UserNotFoundException;
import com.emp.mapper.UserMapper;
import com.emp.model.User;
import com.emp.repository.EmployeeRepository;
import com.emp.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository,UserMapper userMapper,EmployeeRepository employeeRepository 
			,PasswordEncoder passwordEncoder) {
		this.userRepository=userRepository;
		this.userMapper=userMapper;
		this.employeeRepository=employeeRepository;
		this.passwordEncoder=passwordEncoder;
	}
	
	public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
		
		User user = userMapper.userDtoToUser(userRequestDTO);
		user.setEmployee(employeeRepository.findById(userRequestDTO.getEmployeeId())
				.orElseThrow(()->new EmployeeNotFoundException("Employee not found with id:"+userRequestDTO.getEmployeeId())
						));
		user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
		
		
		User saveUser = userRepository.save(user);
		
		return userMapper.userToUserResponse(saveUser);
	}
	public UserResponseDTO getUserById(int id) {
		
		User user =  userRepository.findById(id)
				.orElseThrow(()->new UserNotFoundException("User not found with id: "+id)
				);
		
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
		User existingUser = userRepository.findById(id)
				.orElseThrow(()->new UserNotFoundException("User not found with id: "+id)
				);
		existingUser.setUsername(userRequestDTO.getUsername());
		existingUser.setRole(userRequestDTO.getRole());
		existingUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
		existingUser.setEmployee(employeeRepository.findById(userRequestDTO.getEmployeeId())
				.orElseThrow(()->new EmployeeNotFoundException("Employee not found with id:"+userRequestDTO.getEmployeeId())
						)
				);
		
		userRepository.save(existingUser);
		return userMapper.userToUserResponse(existingUser);
	}
	
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}
}
