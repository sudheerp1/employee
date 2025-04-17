package com.backend.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.employee.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {}