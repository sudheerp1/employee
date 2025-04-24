package com.backend.employee.mapper;

import org.springframework.stereotype.Component;

import com.backend.employee.dto.UserDTO;
import com.backend.employee.entity.User;

@Component
public class UserMapper {

	// Convert UserDTO to User Entity
	public static User toEntity(UserDTO userDTO) {
		if (userDTO == null) {
			return null;
		}
		User user = new User();
		user.setUserName(userDTO.getUserName());
		user.setPassword(userDTO.getPassword());

		if (userDTO.getRole() != null) {
			user.setRole(userDTO.getRole()); // Convert Role from DTO to Entity
		}
		return user;
	}

	// Convert User Entity to UserDTO
	public static UserDTO toDTO(User user) {
		if (user == null) {
			return null;
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(user.getUserName());
		userDTO.setId(user.getId());

		// Convert Role from Entity to DTO
		if (user.getRole() != null) {
			userDTO.setRole(user.getRole());
		}
		return userDTO;
	}
}
