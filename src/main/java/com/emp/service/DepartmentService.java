package com.emp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emp.model.Department;
import com.emp.repository.DepartmentRepository;

@Service
public class DepartmentService {

	private final DepartmentRepository departmentRepository;
	
	public DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository=departmentRepository;
	}
	
	public Department saveDeptDepartment(Department department) {
		return departmentRepository.save(department);
	}
	
	public Department getDeptDepartment(int id) {
		return departmentRepository.findById(id).orElse(null);
	}
	
	public List<Department> getAllDepartments(){
		return departmentRepository.findAll();
	}
	
	public Department updateDepartment(int id ,Department department) {
		Department existingDepartment = departmentRepository.findById(id).orElse(null);
		existingDepartment.setName(department.getName());
		existingDepartment.setDescription(department.getDescription());
		
		return departmentRepository.save(existingDepartment);
	}
	
	public void deleteDepartment(int id) {
		departmentRepository.deleteById(id);
	}
}
