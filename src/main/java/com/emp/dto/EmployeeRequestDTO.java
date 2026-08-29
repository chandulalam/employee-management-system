package com.emp.dto;

import java.time.LocalDate;

import com.emp.model.Status;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {

	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@Pattern(regexp = "\\d{10}")
	private String phone;
	@Positive
	private double salary;
	@NotNull
	private LocalDate joiningDate;
	@NotNull
	private Status status;
	@Positive
	private int departmentId;
}
