package com.backend.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressDTO {
    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "Block is required")
    private String block;

    @NotBlank(message = "Village is required")
    private String village;

    @NotBlank(message = "Zipcode is required")
    @Pattern(regexp = "^\\d{6}$", message = "Invalid zipcode")
    private String zipcode;
}
