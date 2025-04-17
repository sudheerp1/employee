package com.backend.employee.dto;

import java.time.LocalDate;
import java.util.List;

import com.backend.employee.Enum.AccessStatus;
import com.backend.employee.Enum.Designation;
import com.backend.employee.Enum.Gender;
import com.backend.employee.Enum.Salutation;
import com.backend.employee.validation.DOBRange;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmployeeDTO {

    @NotNull(message = "Salutation is required")
    private Salutation salutation;

    private String photoPath;

    @NotBlank(message = "First Name is required")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Middle Name is required")
    @Size(min = 1, max = 50, message = "Middle Name must be between 1 and 50 characters")
    private String middleName;

    @NotBlank(message = "Last Name is required")
    @Size(min = 1, max = 50, message = "Last Name must be between 1 and 50 characters")
    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Nationality is required")
    @Size(min = 2, max = 50, message = "Nationality must be between 2 and 50 characters")
    private String nationality;

    @NotNull(message = "Date of Birth is required")
    @Past(message = "Date of Birth must be in the past")
    @DOBRange(message = "Employee age must be between 18 and 90 years")
    private LocalDate dob;

    @Valid
    @NotNull(message = "Contact details are required")
    private ContactDTO contact;

    @Valid
    @NotNull(message = "Address is required")
    private AddressDTO address;

    @Valid
    @NotNull(message = "Bank details are required")
    private BankDetailDTO bankDetail;

    private List<@Valid DocumentDTO> documents; // Lombok will generate getter and setter for this

    @Size(max = 255, message = "Education detail must not exceed 255 characters")
    private String education;

    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 70, message = "Experience must be a reasonable value")
    private Integer experience;

    @NotNull(message = "Designation is required")
    private Designation designation;

    @NotNull(message = "Access status is required")
    private AccessStatus accessStatus;
}

