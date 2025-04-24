package com.backend.employee.dto;

import com.backend.employee.Enum.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
	private long id;
	@NotNull(message = "user name should not be empty")
	private String userName;
	@NotNull
	private String password;
	@NotNull(message = "role  should not be empty")
	private Role role; // Use String to represent the Role enum
}
