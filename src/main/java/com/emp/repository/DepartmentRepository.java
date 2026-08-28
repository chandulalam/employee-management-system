package com.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
