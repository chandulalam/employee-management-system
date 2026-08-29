package com.emp.dto;

import com.emp.model.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotNull
	private Role role;
	@Positive
	private int employeeId;
	
}
