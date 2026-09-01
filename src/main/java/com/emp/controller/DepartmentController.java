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

import com.emp.dto.DepartmentRequestDTO;
import com.emp.dto.DepartmentResponseDTO;
import com.emp.service.DepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/dept")
public class DepartmentController {
	
	private final DepartmentService departmentService;

	DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@Operation(
			summary = "Save department",
			description = "Accessible only by ADMIN"
	)
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/save")
	public DepartmentResponseDTO saveDepartment(@Valid @RequestBody DepartmentRequestDTO departmentRequestDTO ) {
		return departmentService.saveDeptDepartment(departmentRequestDTO);
	}
	
	@Operation(
			summary = "Get department by ID",
			description = "Accessible by authenticated users"
	)
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/get/{id}")
	public DepartmentResponseDTO getDepartment(@PathVariable int id) {
		return departmentService.getDepartment(id);
	}
	
	@Operation(
			summary = "Get all departments",
			description = "Accessible by authenticated users"
	)
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/all")
	public List<DepartmentResponseDTO> getAllDepartments(){
		return departmentService.getAllDepartments();
	}
	
	@Operation(
			summary = "Update department by ID",
			description = "Accessible only by ADMIN"
	)
	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/update/{id}")
	public DepartmentResponseDTO updateDepartment(@PathVariable int id,@Valid @RequestBody DepartmentRequestDTO departmentRequestDTO) {
		return departmentService.updateDepartment(id, departmentRequestDTO);
	}
	
	@Operation(
			summary = "Delete department by ID",
			description = "Accessible only by ADMIN"
	)
	@SecurityRequirement(name = "bearerAuth")
	@DeleteMapping("/delete/{id}")
	public void deleteDepartment(@PathVariable int id) {
		departmentService.deleteDepartment(id);
	}

}
