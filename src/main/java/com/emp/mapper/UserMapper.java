package com.emp.mapper;

import org.springframework.stereotype.Component;

import com.emp.dto.UserRequestDTO;
import com.emp.dto.UserResponseDTO;
import com.emp.model.User;

@Component
public class UserMapper {
	
	public User userDtoToUser(UserRequestDTO userRequestDTO) {
		User user=new User();
		user.setUsername(userRequestDTO.getUsername());
		user.setPassword(userRequestDTO.getPassword());
		
		return user;

	}
	
	public UserResponseDTO userToUserResponse(User user) {
		UserResponseDTO responseDTO=new UserResponseDTO();
		responseDTO.setId(user.getId());
		responseDTO.setUsername(user.getUsername());
		responseDTO.setRole(user.getRole());
		responseDTO.setEmployeeId(user.getEmployee().getId());
		
		return responseDTO;
	}

}
