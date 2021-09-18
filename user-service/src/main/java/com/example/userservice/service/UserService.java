package com.example.userservice.service;

import com.example.userservice.commons.dto.user.UserDto;
import com.example.userservice.commons.entity.User;

import java.util.List;

public interface UserService {
  UserDto createUser(UserDto userDto);

  UserDto getUserByUserId(String userId);

  List<User> getUserByAll();
}
