package com.emp.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.emp.dto.EmployeeProfileUpdateDTO;
import com.emp.dto.EmployeeRequestDTO;
import com.emp.dto.EmployeeResponseDTO;
import com.emp.exception.DepartmentNotFoundException;
import com.emp.exception.EmployeeNotFoundException;
import com.emp.exception.UserNotFoundException;
import com.emp.mapper.EmployeeMapper;
import com.emp.model.Employee;
import com.emp.model.User;
import com.emp.repository.DepartmentRepository;
import com.emp.repository.EmployeeRepository;
import com.emp.repository.UserRepository;

@Service
public class EmployeeService {
	
	private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	private final  UserRepository userRepository;

	public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeMapper employeeMapper, UserRepository userRepository) {
	        this.employeeRepository = employeeRepository;
	        this.departmentRepository=departmentRepository;
			this.employeeMapper = employeeMapper;
		this.userRepository = userRepository;
	    }
	    
	  
	    
	public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employeeRequestDTO) {
		
		Employee employee = employeeMapper.employeeDtoToEmployee(employeeRequestDTO);
		employee.setDepartment(departmentRepository.findById(employeeRequestDTO.getDepartmentId())
				.orElseThrow(()-> 
					new DepartmentNotFoundException("Department not found with id: " + employeeRequestDTO.getDepartmentId()) )
				);
					
		Employee savedEmployee = employeeRepository.save(employee);
		
	   return  employeeMapper.employeeToEmployeeResponse(savedEmployee);
	   	
	}
	
	public EmployeeResponseDTO getEmployee(int id) {
		
		Employee employee = employeeRepository.findById(id)
		        .orElseThrow(() ->
		                new EmployeeNotFoundException(
		                        "Employee not found with id: " + id
		                ));
		 
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
		
		Employee existingEmployee =employeeRepository.findById(id)
        .orElseThrow(() ->
                new EmployeeNotFoundException(
                        "Employee not found with id: " + id
                ));
		
		
		existingEmployee.setFirstName(employeeRequestDTO.getFirstName());
		existingEmployee.setLastName(employeeRequestDTO.getLastName());
		existingEmployee.setDepartment(departmentRepository.findById(employeeRequestDTO.getDepartmentId())
				.orElseThrow(()-> new DepartmentNotFoundException("Department not found with id:  "+employeeRequestDTO.getDepartmentId())
				)
				);
		existingEmployee.setSalary(employeeRequestDTO.getSalary());
		existingEmployee.setJoiningDate(employeeRequestDTO.getJoiningDate());
		existingEmployee.setEmail(employeeRequestDTO.getEmail());
		existingEmployee.setPhone(employeeRequestDTO.getPhone());
		existingEmployee.setStatus(employeeRequestDTO.getStatus());
		
		Employee updatedEmployee = employeeRepository.save(existingEmployee);
		
		return employeeMapper.employeeToEmployeeResponse(updatedEmployee);
		
		 
		
	}
	
	public void deleteEmployee(int id) {
		
		 Employee employee = employeeRepository.findById(id)
		            .orElseThrow(() ->
		                    new EmployeeNotFoundException(
		                            "Employee not found with id: " + id
		                    ));
		
		employeeRepository.delete(employee);
	}
	
	public EmployeeResponseDTO getMyEmployeeProfile() {

	    String username = SecurityContextHolder.getContext()
	            .getAuthentication()
	            .getName();

	    User user = userRepository.findUserByUsername(username);

	    if (user == null) {
	        throw new UserNotFoundException(
	                "User not found with username: " + username
	        );
	    }

	    Employee employee = user.getEmployee();

	    if (employee == null) {
	        throw new EmployeeNotFoundException(
	                "Employee profile not found for user: " + username
	        );
	    }

	    return employeeMapper.employeeToEmployeeResponse(employee);
	}
	
	public EmployeeResponseDTO updateMyProfile(
	        EmployeeProfileUpdateDTO dto) {

	    String username = SecurityContextHolder.getContext()
	            .getAuthentication()
	            .getName();

	    User user = userRepository.findUserByUsername(username);

	    if (user == null) {
	        throw new UserNotFoundException(
	                "User not found with username: " + username
	        );
	    }

	    Employee employee = user.getEmployee();

	    if (employee == null) {
	        throw new EmployeeNotFoundException(
	                "Employee profile not found for user: " + username
	        );
	    }

	    employee.setFirstName(dto.getFirstName());
	    employee.setLastName(dto.getLastName());
	    employee.setEmail(dto.getEmail());
	    employee.setPhone(dto.getPhone());

	    Employee updatedEmployee = employeeRepository.save(employee);

	    return employeeMapper.employeeToEmployeeResponse(updatedEmployee);
	}
	
}
