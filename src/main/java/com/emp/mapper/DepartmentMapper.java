package com.emp.mapper;

import org.springframework.stereotype.Component;

import com.emp.dto.DepartmentRequestDTO;
import com.emp.dto.DepartmentResponseDTO;
import com.emp.model.Department;

@Component
public class DepartmentMapper {
	
	public Department departmentDtoToDepartment(DepartmentRequestDTO departmentRequestDTO) {
		Department department=new Department();
		department.setName(departmentRequestDTO.getName());
		department.setDescription(departmentRequestDTO.getDescription());
		return department;
	}
	
	public DepartmentResponseDTO departmentToDepartmentResponse(Department department) {
		DepartmentResponseDTO responseDTO=new DepartmentResponseDTO();
		responseDTO.setId(department.getId());
		responseDTO.setName(department.getName());
		responseDTO.setDescription(department.getDescription());
		
		return responseDTO;
	}

}
