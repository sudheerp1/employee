package com.backend.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.employee.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {}
