package com.example.userservice.service;

import com.example.userservice.commons.dto.user.UserDto;
import com.example.userservice.commons.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
  UserDto createUser(UserDto userDto);

  UserDto getUserByUserId(String userId);

  List<UserEntity> getUserByAll();

  UserDto getUserDetailByEmail(String username);
}
