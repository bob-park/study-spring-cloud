package com.example.userservice.service;

import com.example.userservice.commons.dto.user.UserDto;

public interface UserService {
  UserDto createUser(UserDto userDto);
}
