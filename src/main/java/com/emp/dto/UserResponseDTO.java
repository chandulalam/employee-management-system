package com.emp.dto;

import com.emp.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserResponseDTO {
	
	private int id;
	private String username;
	private Role role;
	private int employeeId;

}
