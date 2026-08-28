package com.emp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emp.model.Employee;
import com.emp.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	  private final EmployeeRepository employeeRepository;

	    public EmployeeService(EmployeeRepository employeeRepository) {
	        this.employeeRepository = employeeRepository;
	    }

	    
	public Employee saveEmployee(Employee employee) {
		
		return employeeRepository.save(employee);
		
	}
	
	public Employee getEmployee(int id) {
		
		return employeeRepository.findById(id).orElse(null);
	}
	
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	public Employee updateEmployee(int id,Employee employee) {
		
		Employee existingEmployee = employeeRepository.findById(id).orElse(null);
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setDepartment(employee.getDepartment());
		existingEmployee.setSalary(employee.getSalary());
		existingEmployee.setJoiningDate(employee.getJoiningDate());
		existingEmployee.setEmail(employee.getEmail());
		existingEmployee.setPhone(employee.getPhone());
		existingEmployee.setStatus(employee.getStatus());
		
		return employeeRepository.save(existingEmployee);	
	}
	
	public void deleteEmployee(int id) {
		employeeRepository.deleteById(id);
	}
	
}
