package com.emp.dto;

import java.time.LocalDate;

import com.emp.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {
	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private double salary;
	private LocalDate joiningDate;
	private Status status;
	private int departmentId;

}
