package com.backend.employee;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.backend.employee.Enum.AccessStatus;
import com.backend.employee.Enum.Designation;
import com.backend.employee.Enum.DocumentType;
import com.backend.employee.Enum.Gender;
import com.backend.employee.Enum.Salutation;
import com.backend.employee.dto.AddressDTO;
import com.backend.employee.dto.ContactDTO;
import com.backend.employee.dto.DocumentDTO;
import com.backend.employee.dto.BankDetailDTO;
import com.backend.employee.dto.EmployeeDTO;
import com.backend.employee.entity.Employee;
import com.backend.employee.mapper.EmployeeMapper;
import java.time.LocalDate;
import java.util.List;

class EmployeeMapperTest {

    private EmployeeMapper employeeMapper;

    @BeforeEach
    void setUp() {
        employeeMapper = new EmployeeMapper();
    }

    @Test
    void testToEntity() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setSalutation(Salutation.MR);
        dto.setGender(Gender.MALE);
        dto.setDob(LocalDate.parse("1990-01-01"));
        dto.setNationality("American");
        dto.setEducation("Bachelor's");
        dto.setExperience(5);
        dto.setDesignation(Designation.MANAGER);
        dto.setAccessStatus(AccessStatus.ACTIVE);

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactNumber("1234567890");
        contactDTO.setEmail("john.doe@example.com");
        dto.setContact(contactDTO);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCountry("USA");
        addressDTO.setState("California");
        addressDTO.setZipcode("90001");
        dto.setAddress(addressDTO);

        DocumentDTO aadhaarDoc = new DocumentDTO();
        aadhaarDoc.setDocumentType(DocumentType.AADHAR_NUMBER);
        aadhaarDoc.setDocumentNumber("1234-5678-9012");
        aadhaarDoc.setFilePath("/path/to/aadhaar/file");
        dto.setDocuments(List.of(aadhaarDoc));

        BankDetailDTO bankDetailDTO = new BankDetailDTO();
        bankDetailDTO.setPassbookFilePath("/path/to/passbook/file");
        dto.setBankDetail(bankDetailDTO);

        dto.setPhotoPath("/path/to/photo/file");

        Employee employee = employeeMapper.toEntity(dto);

        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals(Salutation.MR, employee.getSalutation());
        assertEquals(Gender.MALE, employee.getGender());
        assertEquals(LocalDate.parse("1990-01-01"), employee.getDob());
        assertEquals("American", employee.getNationality());
        assertEquals("Bachelor's", employee.getEducation());
        assertEquals(5, employee.getExperience());
        assertEquals(Designation.MANAGER, employee.getDesignation());
        assertEquals(AccessStatus.ACTIVE, employee.getAccessStatus());

        assertNotNull(employee.getContact());
        assertEquals("1234567890", employee.getContact().getContactNumber());
        assertEquals("john.doe@example.com", employee.getContact().getEmail());

        assertNotNull(employee.getAddress());
        assertEquals("USA", employee.getAddress().getCountry());
        assertEquals("California", employee.getAddress().getState());
        assertEquals("90001", employee.getAddress().getZipcode());

        assertNotNull(employee.getDocuments());
        assertEquals(1, employee.getDocuments().size());
        assertEquals(DocumentType.AADHAR_NUMBER, employee.getDocuments().get(0).getDocumentType());
        assertEquals("1234-5678-9012", employee.getDocuments().get(0).getNumber());
        assertEquals("/path/to/aadhaar/file", employee.getDocuments().get(0).getFilePath());

        assertNotNull(employee.getBankDetail());
        assertEquals("/path/to/passbook/file", employee.getBankDetail().getPassbookPath());

        assertNotNull(employee.getPhotoPath());
        assertEquals("/path/to/photo/file", employee.getPhotoPath());
    }
}
