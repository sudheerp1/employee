package com.backend.employee.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.backend.employee.Enum.DocumentType;
import com.backend.employee.dto.DocumentDTO;
import com.backend.employee.dto.EmployeeDTO;
import com.backend.employee.entity.Employee;
import com.backend.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;
    private final ObjectMapper objectMapper;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

    // Create employee with JSON body only
    @PostMapping
    public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeDTO dto) {
        Employee saved = service.save(dto);
        return ResponseEntity.ok("Employee created with ID: " + saved.getId());
    }

    // Create employee with file upload (photo + document + passbook + JSON as text)
    @PostMapping(value = "/multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addEmployeeWithFiles(
            @RequestPart("employee") String employeeJson,
            @RequestPart(value = "document", required = false) MultipartFile documentFile,
            @RequestPart(value = "photo", required = false) MultipartFile photoFile,
            @RequestPart(value = "passbook", required = false) MultipartFile passbookFile) {

        EmployeeDTO employeeDTO;
        try {
            employeeDTO = objectMapper.readValue(employeeJson, EmployeeDTO.class);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid JSON: " + e.getMessage());
        }

        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        // Save photo
        if (photoFile != null && !photoFile.isEmpty()) {
            try {
                String photoName = UUID.randomUUID() + "_" + photoFile.getOriginalFilename();
                File photoDest = new File(UPLOAD_DIR + photoName);
                photoFile.transferTo(photoDest);
                employeeDTO.setPhotoPath("/uploads/" + photoName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error uploading photo: " + e.getMessage());
            }
        }

        // Save document
        if (documentFile != null && !documentFile.isEmpty()) {
            try {
                String docName = UUID.randomUUID() + "_" + documentFile.getOriginalFilename();
                File docDest = new File(UPLOAD_DIR + docName);
                documentFile.transferTo(docDest);

                DocumentDTO docDTO = new DocumentDTO();
                docDTO.setDocumentType(DocumentType.AADHAR_NUMBER); 
                docDTO.setDocumentNumber("");
                docDTO.setFilePath("/uploads/" + docName);

                employeeDTO.setDocuments(List.of(docDTO));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error uploading document: " + e.getMessage());
            }
        }

        // Save passbook
        if (passbookFile != null && !passbookFile.isEmpty()) {
            try {
                String passbookName = UUID.randomUUID() + "_" + passbookFile.getOriginalFilename();
                File passbookDest = new File(UPLOAD_DIR + passbookName);
                passbookFile.transferTo(passbookDest);
                employeeDTO.getBankDetail().setPassbookFilePath("/uploads/" + passbookName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error uploading passbook: " + e.getMessage());
            }
        }

        Employee saved = service.save(employeeDTO);
        return ResponseEntity.ok("Employee created with ID: " + saved.getId());
    }

    // Get all employees
    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }
    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String filePath = UPLOAD_DIR + filename;
            file.transferTo(new File(filePath));
            return ResponseEntity.ok("/uploads/" + filename);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());
        }
    }
}
