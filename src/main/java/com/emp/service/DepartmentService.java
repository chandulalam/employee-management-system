package com.emp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emp.dto.DepartmentRequestDTO;
import com.emp.dto.DepartmentResponseDTO;
import com.emp.mapper.DepartmentMapper;
import com.emp.model.Department;
import com.emp.repository.DepartmentRepository;

@Service
public class DepartmentService {

	private final DepartmentRepository departmentRepository;
	private final DepartmentMapper departmentMapper;
	
	public DepartmentService(DepartmentMapper departmentMapper, DepartmentRepository departmentRepository) {
		this.departmentRepository=departmentRepository;
		this.departmentMapper=departmentMapper;
	}
	
	public DepartmentResponseDTO saveDeptDepartment(DepartmentRequestDTO departmentRequestDTO) {
		
		Department department = departmentMapper.departmentDtoToDepartment(departmentRequestDTO);
		Department saveDepartment = departmentRepository.save(department);
		
		return departmentMapper.departmentToDepartmentResponse(saveDepartment);
	}
	
	public DepartmentResponseDTO getDepartment(int id) {
		
		Department department = departmentRepository.findById(id).orElse(null);
		return departmentMapper.departmentToDepartmentResponse(department);
	}
	
	public List<DepartmentResponseDTO> getAllDepartments(){
		 List<Department> allDepartments = departmentRepository.findAll();
		return  allDepartments
		 	.stream()
		 	.map(departmentMapper::departmentToDepartmentResponse)
		 	.toList();
		 
		 
	}
	
	public DepartmentResponseDTO updateDepartment(int id ,DepartmentRequestDTO departmentRequestDTO) {
		Department existingDepartment = departmentRepository.findById(id).orElse(null);
		existingDepartment.setName(departmentRequestDTO.getName());
		existingDepartment.setDescription(departmentRequestDTO.getDescription());
		
		 Department updatedDepartment = departmentRepository.save(existingDepartment);
		 return departmentMapper.departmentToDepartmentResponse(updatedDepartment);
	}
	
	public void deleteDepartment(int id) {
		departmentRepository.deleteById(id);
	}
}
