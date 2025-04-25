package com.backend.employee;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.backend.employee.controller.EmployeeController;
import com.backend.employee.dto.EmployeeDTO;
import com.backend.employee.entity.Employee;
import com.backend.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;

	private EmployeeDTO employeeDTO;
	private Employee employee;

	@BeforeEach
	void setUp() {
		// Initialize the EmployeeDTO
		employeeDTO = new EmployeeDTO();
		employeeDTO.setFirstName("John");
		employeeDTO.setLastName("Doe");
		employeeDTO.setEmail("john.doe@example.com");
		employeeDTO.setDob(LocalDate.parse("1990-01-01"));

		// Mock the Employee entity
		employee = new Employee();
		employee.setId(1L);
		employee.setFirstName("John");
		employee.setLastName("Doe");

		// Mock dependencies and data setup
	}

	@Test
	void testAddEmployee() throws Exception {
		when(employeeService.save(any(EmployeeDTO.class))).thenReturn(employee);

		String employeeJson = objectMapper.writeValueAsString(employeeDTO);

		mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(employeeJson))
				.andExpect(status().isOk()).andExpect(content().string("Employee created with ID: 1"));

		verify(employeeService, times(1)).save(any(EmployeeDTO.class));
	}

	@Test
	void testAddEmployeeWithFiles() throws Exception {
		when(employeeService.save(any(EmployeeDTO.class))).thenReturn(employee);

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

	@Test
	void testGetAllEmployees() throws Exception {
		List<Employee> employees = List.of(employee);

		when(employeeService.getAll()).thenReturn(employees);

		mockMvc.perform(get("/api/employees")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].firstName").value("John"));

		verify(employeeService, times(1)).getAll();
	}

	@Test
	void testGetEmployeeById() throws Exception {
		when(employeeService.getById(1L)).thenReturn(Optional.of(employee));

		mockMvc.perform(get("/api/employees/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.firstName").value("John")).andExpect(jsonPath("$.lastName").value("Doe"));

		verify(employeeService, times(1)).getById(1L);
	}

	@Test
	void testGetEmployeeByIdNotFound() throws Exception {
		when(employeeService.getById(999L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/employees/999")).andExpect(status().isNotFound());

		verify(employeeService, times(1)).getById(999L);
	}

	@Test
	void testUploadDocument() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "document.pdf", MediaType.APPLICATION_PDF_VALUE,
				"dummy-document-content".getBytes());

		mockMvc.perform(multipart("/api/employees/upload").file(file)).andExpect(status().isOk())
				.andExpect(content().string("/uploads/document.pdf"));
	}
}
