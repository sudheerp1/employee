package com.backend.employee;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.backend.employee.Enum.AccessStatus;
import com.backend.employee.Enum.Designation;
import com.backend.employee.Enum.DocumentType;
import com.backend.employee.Enum.Gender;
import com.backend.employee.Enum.Role;
import com.backend.employee.Enum.Salutation;
import com.backend.employee.controller.EmployeeController;
import com.backend.employee.dto.AddressDTO;
import com.backend.employee.dto.BankDetailDTO;
import com.backend.employee.dto.ContactDTO;
import com.backend.employee.dto.DocumentDTO;
import com.backend.employee.dto.EmployeeDTO;
import com.backend.employee.dto.UserDTO;
import com.backend.employee.entity.Employee;
import com.backend.employee.entity.User;
import com.backend.employee.mapper.EmployeeMapper;
import com.backend.employee.security.JwtUtil;
import com.backend.employee.service.EmployeeService;
import com.backend.employee.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EmployeeService employeeService;

	@MockitoBean
	private UserService userService;

	@MockitoBean
	private EmployeeMapper employeeMapper;

	@MockitoBean
	private JwtUtil jwtUtil;

	@Autowired
	private ObjectMapper objectMapper; // Use the Spring-managed ObjectMapper

	private EmployeeDTO employeeDTO;
	private Employee employee;
	private User user;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Initialize Mockito beans

		// Initialize ContactDTO
		ContactDTO contactDTO = new ContactDTO();
		contactDTO.setContactNumber("1234567890");
		contactDTO.setEmail("john.doe.contact@example.com");
		contactDTO.setAltNumber("0987654321");
		contactDTO.setAltNumberType("Home");
		contactDTO.setRelation("Brother");
		contactDTO.setFatherName("James Doe");

		// Initialize AddressDTO
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setCountry("India");
		addressDTO.setState("Maharashtra");
		addressDTO.setDistrict("Mumbai");
		addressDTO.setBlock("Andheri");
		addressDTO.setVillage("Versova");
		addressDTO.setZipcode("400053");

		// Initialize BankDetailDTO
		BankDetailDTO bankDetailDTO = new BankDetailDTO();
		bankDetailDTO.setBankName("HDFC Bank");
		bankDetailDTO.setAccountNumber("123456789012345678");
		bankDetailDTO.setBranchName("Andheri West");
		bankDetailDTO.setIfscCode("HDFC0001234");
		bankDetailDTO.setPassbookFilePath("/docs/passbook.jpg");

		// Initialize UserDTO
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName("john_doe1");
		userDTO.setPassword("password123");
		userDTO.setRole(Role.ADMIN);

		// Initialize DocumentDTO
		DocumentDTO aadhaarDocument = new DocumentDTO();
		aadhaarDocument.setDocumentType(DocumentType.AADHAR_NUMBER);
		aadhaarDocument.setDocumentNumber("1234-5678-9012");
		aadhaarDocument.setFilePath("/docs/aadhaar.jpg");

		DocumentDTO panDocument = new DocumentDTO();
		panDocument.setDocumentType(DocumentType.PAN_NUMBER);
		panDocument.setDocumentNumber("ABCDE1234F");
		panDocument.setFilePath("/docs/pan.jpg");

		// Initialize EmployeeDTO
		employeeDTO = new EmployeeDTO();
		employeeDTO.setSalutation(Salutation.MR);
		employeeDTO.setEmail("john.doe@example.com");
		employeeDTO.setPassword("password123");
		employeeDTO.setPhotoPath("/images/profile.jpg");
		employeeDTO.setFirstName("John");
		employeeDTO.setMiddleName("M");
		employeeDTO.setLastName("Doe");
		employeeDTO.setGender(Gender.MALE);
		employeeDTO.setNationality("Indian");
		employeeDTO.setDob(LocalDate.parse("1990-01-01"));
		employeeDTO.setContact(contactDTO);
		employeeDTO.setAddress(addressDTO);
		employeeDTO.setBankDetail(bankDetailDTO);
		employeeDTO.setUser(userDTO);
		employeeDTO.setDocuments(Arrays.asList(aadhaarDocument, panDocument));
		employeeDTO.setEducation("Bachelor of Science in Computer Engineering");
		employeeDTO.setExperience(5);
		employeeDTO.setDesignation(Designation.MANAGER);
		employeeDTO.setAccessStatus(AccessStatus.ACTIVE);

		// Initialize Employee entity
		employee = new Employee();
		employee.setId(1L);
		employee.setFirstName("John");
		employee.setLastName("Doe");
		user = new User();
		user.setId(1L);
		user.setPassword("password123");
		user.setRole(Role.ADMIN);
		user.setUserName("john_doe1");
		employee.setUser(user);

		// Mock Mapper
		when(employeeMapper.toEntity(any(EmployeeDTO.class))).thenReturn(employee);

		// Mock UserService
		when(userService.save(any(UserDTO.class))).thenReturn(user);

		// Mock EmployeeService
		when(employeeService.save(any(EmployeeDTO.class))).thenReturn(employee);
		when(employeeService.getAll()).thenReturn(List.of(employee));
		when(employeeService.getById(any(Long.class))).thenReturn(Optional.of(employee));
	}

	@Test
	void testAddEmployee() throws Exception {
		String employeeJson = objectMapper.writeValueAsString(employeeDTO);

		mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(employeeJson))
				.andExpect(status().isOk()).andExpect(content().string("Employee created with ID: 1"));

		verify(employeeService, times(1)).save(any(EmployeeDTO.class));
	}

	@Test
	void testAddEmployeeWithFiles() throws Exception {
		MockMultipartFile documentFile = new MockMultipartFile("document", "document.pdf",
				MediaType.APPLICATION_PDF_VALUE, "dummy-document-content".getBytes());
		MockMultipartFile photoFile = new MockMultipartFile("photo", "photo.jpg", MediaType.IMAGE_JPEG_VALUE,
				"dummy-photo-content".getBytes());
		MockMultipartFile passbookFile = new MockMultipartFile("passbook", "passbook.jpg", MediaType.IMAGE_JPEG_VALUE,
				"dummy-passbook-content".getBytes());
		MockMultipartFile employeeJson = new MockMultipartFile("employee", "", MediaType.APPLICATION_JSON_VALUE,
				objectMapper.writeValueAsBytes(employeeDTO));

		mockMvc.perform(multipart("/api/employees/multipart").file(employeeJson).file(documentFile).file(photoFile)
				.file(passbookFile).contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().isOk())
				.andExpect(content().string("Employee created with ID: 1"));

		verify(employeeService, times(1)).save(any(EmployeeDTO.class));
	}
}
