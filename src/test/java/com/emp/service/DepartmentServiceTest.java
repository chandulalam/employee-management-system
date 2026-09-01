package com.emp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emp.dto.DepartmentRequestDTO;
import com.emp.dto.DepartmentResponseDTO;
import com.emp.exception.DepartmentNotFoundException;
import com.emp.mapper.DepartmentMapper;
import com.emp.model.Department;
import com.emp.repository.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

	@Mock
	private DepartmentRepository departmentRepository;

	@Mock
	private DepartmentMapper departmentMapper;

	@InjectMocks
	private DepartmentService departmentService;

	@Test
	void saveDeptDepartment_shouldReturnSavedDepartment() {
		DepartmentRequestDTO request = new DepartmentRequestDTO("IT", "Information Technology");
		Department department = new Department("IT", "Information Technology");
		Department savedDepartment = new Department(1, "IT", "Information Technology");
		DepartmentResponseDTO response = new DepartmentResponseDTO(1, "IT", "Information Technology");

		when(departmentMapper.departmentDtoToDepartment(request)).thenReturn(department);
		when(departmentRepository.save(department)).thenReturn(savedDepartment);
		when(departmentMapper.departmentToDepartmentResponse(savedDepartment)).thenReturn(response);

		DepartmentResponseDTO result = departmentService.saveDeptDepartment(request);

		assertEquals(response, result);
		verify(departmentRepository).save(department);
	}

	@Test
	void getDepartment_shouldReturnDepartment_whenFound() {
		Department department = new Department(1, "HR", "Human Resources");
		DepartmentResponseDTO response = new DepartmentResponseDTO(1, "HR", "Human Resources");

		when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
		when(departmentMapper.departmentToDepartmentResponse(department)).thenReturn(response);

		DepartmentResponseDTO result = departmentService.getDepartment(1);

		assertEquals(response, result);
	}

	@Test
	void getDepartment_shouldThrowException_whenNotFound() {
		when(departmentRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartment(99));
	}

	@Test
	void getAllDepartments_shouldReturnAllDepartments() {
		Department dept1 = new Department(1, "IT", "Information Technology");
		Department dept2 = new Department(2, "HR", "Human Resources");
		DepartmentResponseDTO response1 = new DepartmentResponseDTO(1, "IT", "Information Technology");
		DepartmentResponseDTO response2 = new DepartmentResponseDTO(2, "HR", "Human Resources");

		when(departmentRepository.findAll()).thenReturn(List.of(dept1, dept2));
		when(departmentMapper.departmentToDepartmentResponse(dept1)).thenReturn(response1);
		when(departmentMapper.departmentToDepartmentResponse(dept2)).thenReturn(response2);

		List<DepartmentResponseDTO> result = departmentService.getAllDepartments();

		assertEquals(2, result.size());
		assertEquals(response1, result.get(0));
		assertEquals(response2, result.get(1));
	}

	@Test
	void updateDepartment_shouldReturnUpdatedDepartment_whenFound() {
		DepartmentRequestDTO request = new DepartmentRequestDTO("Finance", "Finance Department");
		Department existingDepartment = new Department(1, "IT", "Information Technology");
		Department updatedDepartment = new Department(1, "Finance", "Finance Department");
		DepartmentResponseDTO response = new DepartmentResponseDTO(1, "Finance", "Finance Department");

		when(departmentRepository.findById(1)).thenReturn(Optional.of(existingDepartment));
		when(departmentRepository.save(existingDepartment)).thenReturn(updatedDepartment);
		when(departmentMapper.departmentToDepartmentResponse(updatedDepartment)).thenReturn(response);

		DepartmentResponseDTO result = departmentService.updateDepartment(1, request);

		assertEquals("Finance", existingDepartment.getName());
		assertEquals("Finance Department", existingDepartment.getDescription());
		assertEquals(response, result);
		verify(departmentRepository).save(existingDepartment);
	}

	@Test
	void updateDepartment_shouldThrowException_whenNotFound() {
		DepartmentRequestDTO request = new DepartmentRequestDTO("Finance", "Finance Department");

		when(departmentRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(DepartmentNotFoundException.class,
				() -> departmentService.updateDepartment(99, request));
	}

	@Test
	void deleteDepartment_shouldDeleteDepartment_whenFound() {
		Department department = new Department(1, "IT", "Information Technology");

		when(departmentRepository.findById(1)).thenReturn(Optional.of(department));

		departmentService.deleteDepartment(1);

		verify(departmentRepository).delete(department);
	}

	@Test
	void deleteDepartment_shouldThrowException_whenNotFound() {
		when(departmentRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(DepartmentNotFoundException.class, () -> departmentService.deleteDepartment(99));
		verify(departmentRepository, org.mockito.Mockito.never()).delete(any());
	}
}
