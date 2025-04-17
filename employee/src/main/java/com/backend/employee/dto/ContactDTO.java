package com.backend.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ContactDTO {
    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^\\d{10,15}$", message = "Invalid contact number")
    private String contactNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\d{10,15}$", message = "Invalid contact number")
    private String altNumber;

    private String altNumberType;

    @NotBlank(message = "Relation is required")
    private String relation;
 
    @NotBlank(message = "Father Name is required")
    private String fatherName;
}
