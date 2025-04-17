package com.backend.employee.service;

import com.backend.employee.dto.EmployeeDTO;
import com.backend.employee.entity.Employee;
import com.backend.employee.mapper.EmployeeMapper;
import com.backend.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepo;

    @Mock
    private EmployeeMapper mapper;

    @InjectMocks
    private EmployeeService service;

    @Test
    void testSaveEmployee() {
        // Given
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("John");

        Employee employee = new Employee();
        employee.setFirstName("John");

        when(mapper.toEntity(dto)).thenReturn(employee);
        when(employeeRepo.save(employee)).thenReturn(employee);

        // When
        Employee saved = service.save(dto);

        // Then
        assertNotNull(saved);
        assertEquals("John", saved.getFirstName());
        verify(mapper, times(1)).toEntity(dto);
        verify(employeeRepo, times(1)).save(employee);
    }

    @Test
    void testGetAllEmployees() {
        // Given
        Employee emp1 = new Employee();
        emp1.setFirstName("Alice");

        Employee emp2 = new Employee();
        emp2.setFirstName("Bob");

        when(employeeRepo.findAll()).thenReturn(List.of(emp1, emp2));

        // When
        List<Employee> employees = service.getAll();

        // Then
        assertEquals(2, employees.size());
        assertEquals("Alice", employees.get(0).getFirstName());
        assertEquals("Bob", employees.get(1).getFirstName());
    }

    @Test
    void testGetEmployeeById_Found() {
        // Given
        Long id = 1L;
        Employee emp = new Employee();
        emp.setId(id);
        emp.setFirstName("Jane");

        when(employeeRepo.findById(id)).thenReturn(Optional.of(emp));

        // When
        Optional<Employee> result = service.getById(id);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getFirstName());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // Given
        Long id = 99L;
        when(employeeRepo.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Employee> result = service.getById(id);

        // Then
        assertTrue(result.isEmpty());
    }
}
