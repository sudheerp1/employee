package com.backend.employee.service;

import org.springframework.stereotype.Service;

import com.backend.employee.dto.UserDTO;
import com.backend.employee.entity.User;
import com.backend.employee.mapper.UserMapper;
import com.backend.employee.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepo;

	public UserDTO sav(UserDTO userDto) {
		User user = UserMapper.toEntity(userDto);
		User savedUser = userRepo.save(user);
		log.info("saving the user with userName " + user.getUserName());
		return UserMapper.toDTO(savedUser);
	}
}
