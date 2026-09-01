package com.emp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.emp.dto.EmployeeProfileUpdateDTO;
import com.emp.dto.EmployeeRequestDTO;
import com.emp.dto.EmployeeResponseDTO;
import com.emp.exception.DepartmentNotFoundException;
import com.emp.exception.EmployeeNotFoundException;
import com.emp.exception.UserNotFoundException;
import com.emp.mapper.EmployeeMapper;
import com.emp.model.Department;
import com.emp.model.Employee;
import com.emp.model.Role;
import com.emp.model.Status;
import com.emp.model.User;
import com.emp.repository.DepartmentRepository;
import com.emp.repository.EmployeeRepository;
import com.emp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@Mock
	private EmployeeMapper employeeMapper;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private DepartmentRepository departmentRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private EmployeeService employeeService;

	@BeforeEach
	void setUpSecurityContext() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("testuser", "password"));
	}

	@AfterEach
	void clearSecurityContext() {
		SecurityContextHolder.clearContext();
	}

	@Test
	void saveEmployee_shouldReturnSavedEmployee() {
		EmployeeRequestDTO request = new EmployeeRequestDTO(
				"John", "Doe", "john@example.com", "9876543210",
				50000, LocalDate.of(2024, 1, 15), Status.ACTIVE, 1);
		Department department = new Department(1, "IT", "Information Technology");
		Employee employee = new Employee();
		Employee savedEmployee = new Employee(1, "John", "Doe", "john@example.com",
				"9876543210", 50000, LocalDate.of(2024, 1, 15), Status.ACTIVE, department);
		EmployeeResponseDTO response = new EmployeeResponseDTO();

		when(employeeMapper.employeeDtoToEmployee(request)).thenReturn(employee);
		when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
		when(employeeRepository.save(employee)).thenReturn(savedEmployee);
		when(employeeMapper.employeeToEmployeeResponse(savedEmployee)).thenReturn(response);

		EmployeeResponseDTO result = employeeService.saveEmployee(request);

		assertEquals(response, result);
		assertEquals(department, employee.getDepartment());
	}

	@Test
	void saveEmployee_shouldThrowException_whenDepartmentNotFound() {
		EmployeeRequestDTO request = new EmployeeRequestDTO(
				"John", "Doe", "john@example.com", "9876543210",
				50000, LocalDate.of(2024, 1, 15), Status.ACTIVE, 99);
		Employee employee = new Employee();

		when(employeeMapper.employeeDtoToEmployee(request)).thenReturn(employee);
		when(departmentRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(DepartmentNotFoundException.class, () -> employeeService.saveEmployee(request));
		verify(employeeRepository, never()).save(any());
	}

	@Test
	void getEmployee_shouldReturnEmployee_whenFound() {
		Department department = new Department(1, "IT", "Information Technology");
		Employee employee = new Employee(1, "John", "Doe", "john@example.com",
				"9876543210", 50000, LocalDate.of(2024, 1, 15), Status.ACTIVE, department);
		EmployeeResponseDTO response = new EmployeeResponseDTO();

		when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
		when(employeeMapper.employeeToEmployeeResponse(employee)).thenReturn(response);

		EmployeeResponseDTO result = employeeService.getEmployee(1);

		assertEquals(response, result);
	}

	@Test
	void getEmployee_shouldThrowException_whenNotFound() {
		when(employeeRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(99));
	}

	@Test
	void getAllEmployees_shouldReturnAllEmployees() {
		Department department = new Department(1, "IT", "Information Technology");
		Employee emp1 = new Employee(1, "John", "Doe", "john@example.com",
				"9876543210", 50000, LocalDate.of(2024, 1, 15), Status.ACTIVE, department);
		Employee emp2 = new Employee(2, "Jane", "Smith", "jane@example.com",
				"9876543211", 60000, LocalDate.of(2024, 2, 1), Status.ACTIVE, department);
		EmployeeResponseDTO response1 = new EmployeeResponseDTO();
		EmployeeResponseDTO response2 = new EmployeeResponseDTO();

		when(employeeRepository.findAll()).thenReturn(List.of(emp1, emp2));
		when(employeeMapper.employeeToEmployeeResponse(emp1)).thenReturn(response1);
		when(employeeMapper.employeeToEmployeeResponse(emp2)).thenReturn(response2);

		List<EmployeeResponseDTO> result = employeeService.getAllEmployees();

		assertEquals(2, result.size());
	}

	@Test
	void updateEmployee_shouldReturnUpdatedEmployee_whenFound() {
		EmployeeRequestDTO request = new EmployeeRequestDTO(
				"Updated", "Name", "updated@example.com", "9876543210",
				55000, LocalDate.of(2024, 3, 1), Status.INACTIVE, 2);
		Department oldDepartment = new Department(1, "IT", "Information Technology");
		Department newDepartment = new Department(2, "HR", "Human Resources");
		Employee existingEmployee = new Employee(1, "John", "Doe", "john@example.com",
				"9876543210", 50000, LocalDate.of(2024, 1, 15), Status.ACTIVE, oldDepartment);
		EmployeeResponseDTO response = new EmployeeResponseDTO();

		when(employeeRepository.findById(1)).thenReturn(Optional.of(existingEmployee));
		when(departmentRepository.findById(2)).thenReturn(Optional.of(newDepartment));
		when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);
		when(employeeMapper.employeeToEmployeeResponse(existingEmployee)).thenReturn(response);

		EmployeeResponseDTO result = employeeService.updateEmployee(1, request);

		assertEquals("Updated", existingEmployee.getFirstName());
		assertEquals("Name", existingEmployee.getLastName());
		assertEquals(newDepartment, existingEmployee.getDepartment());
		assertEquals(Status.INACTIVE, existingEmployee.getStatus());
		assertEquals(response, result);
	}

	@Test
	void updateEmployee_shouldThrowException_whenEmployeeNotFound() {
		EmployeeRequestDTO request = new EmployeeRequestDTO(
				"Updated", "Name", "updated@example.com", "9876543210",
				55000, LocalDate.of(2024, 3, 1), Status.ACTIVE, 1);

		when(employeeRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(99, request));
	}

	@Test
	void updateEmployee_shouldThrowException_whenDepartmentNotFound() {
		EmployeeRequestDTO request = new EmployeeRequestDTO(
				"Updated", "Name", "updated@example.com", "9876543210",
				55000, LocalDate.of(2024, 3, 1), Status.ACTIVE, 99);
		Department department = new Department(1, "IT", "Information Technology");
		Employee existingEmployee = new Employee(1, "John", "Doe", "john@example.com",
				"9876543210", 50000, LocalDate.of(2024, 1, 15), Status.ACTIVE, department);

		when(employeeRepository.findById(1)).thenReturn(Optional.of(existingEmployee));
		when(departmentRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(DepartmentNotFoundException.class, () -> employeeService.updateEmployee(1, request));
	}

	@Test
	void deleteEmployee_shouldDeleteEmployee_whenFound() {
		Department department = new Department(1, "IT", "Information Technology");
		Employee employee = new Employee(1, "John", "Doe", "john@example.com",
				"9876543210", 50000, LocalDate.of(2024, 1, 15), Status.ACTIVE, department);

		when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

		employeeService.deleteEmployee(1);

		verify(employeeRepository).delete(employee);
	}

	@Test
	void deleteEmployee_shouldThrowException_whenNotFound() {
		when(employeeRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(99));
		verify(employeeRepository, never()).delete(any());
	}

	@Test
	void getMyEmployeeProfile_shouldReturnEmployeeProfile() {
		Department department = new Department(1, "IT", "Information Technology");
		Employee employee = new Employee(1, "John", "Doe", "john@example.com",
				"9876543210", 50000, LocalDate.of(2024, 1, 15), Status.ACTIVE, department);
		User user = new User(1, "testuser", "pass", Role.EMPLOYEE, employee);
		EmployeeResponseDTO response = new EmployeeResponseDTO();

		when(userRepository.findUserByUsername("testuser")).thenReturn(user);
		when(employeeMapper.employeeToEmployeeResponse(employee)).thenReturn(response);

		EmployeeResponseDTO result = employeeService.getMyEmployeeProfile();

		assertEquals(response, result);
	}

	@Test
	void getMyEmployeeProfile_shouldThrowException_whenUserNotFound() {
		when(userRepository.findUserByUsername("testuser")).thenReturn(null);

		assertThrows(UserNotFoundException.class, () -> employeeService.getMyEmployeeProfile());
	}

	@Test
	void getMyEmployeeProfile_shouldThrowException_whenEmployeeNotLinked() {
		User user = new User(1, "testuser", "pass", Role.EMPLOYEE, null);

		when(userRepository.findUserByUsername("testuser")).thenReturn(user);

		assertThrows(EmployeeNotFoundException.class, () -> employeeService.getMyEmployeeProfile());
	}

	@Test
	void updateMyProfile_shouldReturnUpdatedEmployeeProfile() {
		EmployeeProfileUpdateDTO request = new EmployeeProfileUpdateDTO(
				"Updated", "Name", "updated@example.com", "9876543210");
		Department department = new Department(1, "IT", "Information Technology");
		Employee employee = new Employee(1, "John", "Doe", "john@example.com",
				"9876543210", 50000, LocalDate.of(2024, 1, 15), Status.ACTIVE, department);
		User user = new User(1, "testuser", "pass", Role.EMPLOYEE, employee);
		EmployeeResponseDTO response = new EmployeeResponseDTO();

		when(userRepository.findUserByUsername("testuser")).thenReturn(user);
		when(employeeRepository.save(employee)).thenReturn(employee);
		when(employeeMapper.employeeToEmployeeResponse(employee)).thenReturn(response);

		EmployeeResponseDTO result = employeeService.updateMyProfile(request);

		assertEquals("Updated", employee.getFirstName());
		assertEquals("Name", employee.getLastName());
		assertEquals("updated@example.com", employee.getEmail());
		assertEquals(response, result);
	}

	@Test
	void updateMyProfile_shouldThrowException_whenUserNotFound() {
		EmployeeProfileUpdateDTO request = new EmployeeProfileUpdateDTO(
				"Updated", "Name", "updated@example.com", "9876543210");

		when(userRepository.findUserByUsername("testuser")).thenReturn(null);

		assertThrows(UserNotFoundException.class, () -> employeeService.updateMyProfile(request));
	}

	@Test
	void updateMyProfile_shouldThrowException_whenEmployeeNotLinked() {
		EmployeeProfileUpdateDTO request = new EmployeeProfileUpdateDTO(
				"Updated", "Name", "updated@example.com", "9876543210");
		User user = new User(1, "testuser", "pass", Role.EMPLOYEE, null);

		when(userRepository.findUserByUsername("testuser")).thenReturn(user);

		assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateMyProfile(request));
	}
}
