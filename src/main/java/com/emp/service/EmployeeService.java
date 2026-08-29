package com.emp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emp.dto.EmployeeRequestDTO;
import com.emp.dto.EmployeeResponseDTO;
import com.emp.mapper.EmployeeMapper;
import com.emp.model.Employee;
import com.emp.repository.DepartmentRepository;
import com.emp.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;

	public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeMapper employeeMapper) {
	        this.employeeRepository = employeeRepository;
	        this.departmentRepository=departmentRepository;
			this.employeeMapper = employeeMapper;
	    }
	    
	  
	    
	public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employeeRequestDTO) {
		
		Employee employee = employeeMapper.employeeDtoToEmployee(employeeRequestDTO);
		employee.setDepartment(departmentRepository.findById(employeeRequestDTO.getDepartmentId()).orElse(null));
		
		Employee savedEmployee = employeeRepository.save(employee);
		
	   return  employeeMapper.employeeToEmployeeResponse(savedEmployee);
	   
		
	}
	
	public EmployeeResponseDTO getEmployee(int id) {
		
		 Employee employee = employeeRepository.findById(id).orElse(null);
		 
		 return  employeeMapper.employeeToEmployeeResponse(employee);
		 
	}
	
	public List<EmployeeResponseDTO> getAllEmployees(){
		
		List<Employee> allEmployees = employeeRepository.findAll();
		
		return allEmployees
				.stream()
				.map(employeeMapper::employeeToEmployeeResponse)
				.toList();
	}
	
	public EmployeeResponseDTO updateEmployee(int id,EmployeeRequestDTO employeeRequestDTO) {
		
		Employee existingEmployee = employeeRepository.findById(id).orElse(null);
		existingEmployee.setFirstName(employeeRequestDTO.getFirstName());
		existingEmployee.setLastName(employeeRequestDTO.getLastName());
		existingEmployee.setDepartment(departmentRepository.findById(employeeRequestDTO.getDepartmentId()).orElse(null));
		existingEmployee.setSalary(employeeRequestDTO.getSalary());
		existingEmployee.setJoiningDate(employeeRequestDTO.getJoiningDate());
		existingEmployee.setEmail(employeeRequestDTO.getEmail());
		existingEmployee.setPhone(employeeRequestDTO.getPhone());
		existingEmployee.setStatus(employeeRequestDTO.getStatus());
		
		Employee updatedEmployee = employeeRepository.save(existingEmployee);
		
		return employeeMapper.employeeToEmployeeResponse(updatedEmployee);
		
		 
		
	}
	
	public void deleteEmployee(int id) {
		employeeRepository.deleteById(id);
	}
	
}
