package com.emp.dto;

import com.emp.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

	private String username;
	private String password;
	private Role role;
	private int employeeId;
	
}
