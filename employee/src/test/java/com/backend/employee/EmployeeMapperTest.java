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
import com.backend.employee.dto.BankDetailDTO;
import com.backend.employee.dto.ContactDTO;
import com.backend.employee.dto.DocumentDTO;
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
    void testToEntity_AllFields() {
        // Create DTO
        EmployeeDTO dto = new EmployeeDTO();
        dto.setSalutation(Salutation.MR);
        dto.setFirstName("John");
        dto.setMiddleName("A");
        dto.setLastName("Doe");
        dto.setGender(Gender.MALE);
        dto.setNationality("American");
        dto.setDob(LocalDate.of(1990, 1, 1));
        dto.setEducation("Bachelor's");
        dto.setExperience(5);
        dto.setDesignation(Designation.MANAGER);
        dto.setAccessStatus(AccessStatus.ACTIVE);
        dto.setPhotoPath("/path/photo.jpg");

        // ContactDTO
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactNumber("9876543210");
        contactDTO.setEmail("john@example.com");
        contactDTO.setAltNumber("1122334455");
        contactDTO.setAltNumberType("Personal");
        contactDTO.setRelation("Father");
        contactDTO.setFatherName("Robert Doe");
        dto.setContact(contactDTO);

        // AddressDTO
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCountry("USA");
        addressDTO.setState("California");
        addressDTO.setDistrict("Los Angeles");
        addressDTO.setBlock("Block A");
        addressDTO.setVillage("Sunset Village");
        addressDTO.setZipcode("90001");
        dto.setAddress(addressDTO);

        // BankDetailDTO
        BankDetailDTO bankDTO = new BankDetailDTO();
        bankDTO.setBankName("Bank of America");
        bankDTO.setAccountNumber("123456789");
        bankDTO.setIfscCode("BOFAUS3N");
        bankDTO.setBranchName("Downtown");
        bankDTO.setPassbookFilePath("/path/passbook.jpg");
        dto.setBankDetail(bankDTO);

        // DocumentDTO
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setDocumentType(DocumentType.AADHAR_NUMBER);
        docDTO.setDocumentNumber("1234-5678-9012");
        docDTO.setFilePath("/docs/aadhar.pdf");
        dto.setDocuments(List.of(docDTO));

        // Convert to Entity
        Employee employee = employeeMapper.toEntity(dto);

        // Assertions
        assertNotNull(employee);
        assertEquals(Salutation.MR, employee.getSalutation());
        assertEquals("John", employee.getFirstName());
        assertEquals("A", employee.getMiddleName());
        assertEquals("Doe", employee.getLastName());
        assertEquals(Gender.MALE, employee.getGender());
        assertEquals("American", employee.getNationality());
        assertEquals(LocalDate.of(1990, 1, 1), employee.getDob());
        assertEquals("Bachelor's", employee.getEducation());
        assertEquals(5, employee.getExperience());
        assertEquals(Designation.MANAGER, employee.getDesignation());
        assertEquals(AccessStatus.ACTIVE, employee.getAccessStatus());
        assertEquals("/path/photo.jpg", employee.getPhotoPath());

        assertNotNull(employee.getContact());
        assertEquals("9876543210", employee.getContact().getContactNumber());
        assertEquals("john@example.com", employee.getContact().getEmail());
        assertEquals("1122334455", employee.getContact().getAltNumber());
        assertEquals("Personal", employee.getContact().getAltNumberType());
        assertEquals("Father", employee.getContact().getRelation());
        assertEquals("Robert Doe", employee.getContact().getFatherName());

        assertNotNull(employee.getAddress());
        assertEquals("USA", employee.getAddress().getCountry());
        assertEquals("California", employee.getAddress().getState());
        assertEquals("Los Angeles", employee.getAddress().getDistrict());
        assertEquals("Block A", employee.getAddress().getBlock());
        assertEquals("Sunset Village", employee.getAddress().getVillage());
        assertEquals("90001", employee.getAddress().getZipcode());

        assertNotNull(employee.getBankDetail());
        assertEquals("Bank of America", employee.getBankDetail().getBankName());
        assertEquals("123456789", employee.getBankDetail().getAccountNumber());
        assertEquals("BOFAUS3N", employee.getBankDetail().getIfscCode());
        assertEquals("Downtown", employee.getBankDetail().getBranchName());
        assertEquals("/path/passbook.jpg", employee.getBankDetail().getPassbookPath());

        assertNotNull(employee.getDocuments());
        assertEquals(1, employee.getDocuments().size());
        assertEquals(DocumentType.AADHAR_NUMBER, employee.getDocuments().get(0).getDocumentType());
        assertEquals("1234-5678-9012", employee.getDocuments().get(0).getNumber());
        assertEquals("/docs/aadhar.pdf", employee.getDocuments().get(0).getFilePath());
        assertEquals(employee, employee.getDocuments().get(0).getEmployee());
    }
}
