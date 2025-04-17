package com.backend.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.employee.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {}
