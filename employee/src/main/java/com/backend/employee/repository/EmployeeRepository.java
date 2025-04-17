package com.backend.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}