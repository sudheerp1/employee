package com.backend.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.employee.entity.BankDetail;

public interface BankDetailRepository extends JpaRepository<BankDetail, Long> {}
