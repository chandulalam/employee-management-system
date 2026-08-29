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

import com.emp.dto.EmployeeRequestDTO;
import com.emp.dto.EmployeeResponseDTO;
import com.emp.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	final EmployeeService employeeService;

	EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@PostMapping("/save")
	public EmployeeResponseDTO saveEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
		return employeeService.saveEmployee(employeeRequestDTO);
	}
	
	@GetMapping("/get/{id}")
	public EmployeeResponseDTO getEmployee(@PathVariable int id) {
		return employeeService.getEmployee(id);
	}
	
	@GetMapping("/all")
	public List<EmployeeResponseDTO> getAllEmployees() {
		return employeeService.getAllEmployees();
	}
	
	@PutMapping("/update/{id}")
	public EmployeeResponseDTO updateEmployees(@PathVariable int id ,@RequestBody EmployeeRequestDTO employeeRequestDTO) {
		return employeeService.updateEmployee(id, employeeRequestDTO);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable int id) {
		employeeService.deleteEmployee(id);
	}

}
