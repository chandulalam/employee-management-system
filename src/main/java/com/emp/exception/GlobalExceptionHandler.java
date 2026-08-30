package com.emp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<String> handleDepartmentNotFoundException(DepartmentNotFoundException deException){
		 return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body(deException.getMessage());
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException employeeNotFoundException){
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(employeeNotFoundException.getMessage());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException userExpection){
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(userExpection.getMessage());
	}
}
