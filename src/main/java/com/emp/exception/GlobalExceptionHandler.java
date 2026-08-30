package com.emp.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.emp.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleDepartmentNotFoundException(DepartmentNotFoundException deException){
		 
		ErrorResponseDTO error = new ErrorResponseDTO(
	            HttpStatus.NOT_FOUND.value(),
	            deException.getMessage(),
	            LocalDateTime.now()
	    );
		
		
		return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body(error);
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleEmployeeNotFoundException(EmployeeNotFoundException employeeNotFoundException){
		
		ErrorResponseDTO error = new ErrorResponseDTO(
	            HttpStatus.NOT_FOUND.value(),
	            employeeNotFoundException.getMessage(),
	            LocalDateTime.now()
	    );
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(error);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException userException){
		
		ErrorResponseDTO error = new ErrorResponseDTO(
	            HttpStatus.NOT_FOUND.value(),
	            userException.getMessage(),
	            LocalDateTime.now()
	    );
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(error);
	}
}
