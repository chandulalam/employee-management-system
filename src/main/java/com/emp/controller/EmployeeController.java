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

import com.emp.dto.EmployeeProfileUpdateDTO;
import com.emp.dto.EmployeeRequestDTO;
import com.emp.dto.EmployeeResponseDTO;
import com.emp.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	private final EmployeeService employeeService;

	EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@Operation(
			summary = "Save employee",
			description = "Accessible only by ADMIN"
	)
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/save")
	public EmployeeResponseDTO saveEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
		return employeeService.saveEmployee(employeeRequestDTO);
	}
	
	@Operation(
			summary = "Get employee by ID",
			description = "Accessible only by ADMIN"
	)
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/get/{id}")
	public EmployeeResponseDTO getEmployee(@PathVariable int id) {
		return employeeService.getEmployee(id);
	}
	
	@Operation(
			summary = "Get all employees",
			description = "Accessible only by ADMIN"
	)
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/all")
	public List<EmployeeResponseDTO> getAllEmployees() {
		return employeeService.getAllEmployees();
	}
	
	@Operation(
			summary = "Update employee by ID",
			description = "Accessible only by ADMIN"
	)
	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/update/{id}")
	public EmployeeResponseDTO updateEmployees(@PathVariable int id ,@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
		return employeeService.updateEmployee(id, employeeRequestDTO);
	}
	
	@Operation(
			summary = "Delete employee by ID",
			description = "Accessible only by ADMIN"
	)
	@SecurityRequirement(name = "bearerAuth")
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable int id) {
		employeeService.deleteEmployee(id);
	}
	
	@Operation(
			summary = "Get my employee profile",
			description = "Accessible by authenticated users"
	)
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/profile")
	public EmployeeResponseDTO getMyProfile() {
	    return employeeService.getMyEmployeeProfile();
	}
	
	@Operation(
			summary = "Update my employee profile",
			description = "Accessible by authenticated users"
	)
	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/profile")
	public EmployeeResponseDTO updateMyProfile(
	        @Valid @RequestBody EmployeeProfileUpdateDTO dto) {

	    return employeeService.updateMyProfile(dto);
	}

}
