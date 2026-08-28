package com.emp.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private double salary;
	private LocalDate joiningDate;
	@Enumerated(EnumType.STRING)
	private Status status;
	@ManyToOne
	private Department department;
	public Employee(String firstName, String lastName, String email, String phone, double salary, LocalDate joiningDate,
			Status status, Department department) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
		this.joiningDate = joiningDate;
		this.status = status;
		this.department = department;
	}

	
	
	
}
