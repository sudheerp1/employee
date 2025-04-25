package com.backend.employee.service;

import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final PasswordEncoder encoder;

	public User save(UserDTO userDto) {
		userDto.setPassword(encoder.encode(userDto.getPassword()));
		User user = UserMapper.toEntity(userDto);
		User savedUser = userRepo.save(user);
		log.info("saving the user with userName " + user.getUserName());
		return savedUser;
	}
}
