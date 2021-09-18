package com.example.userservice.service.impl;

import com.example.userservice.commons.dto.user.UserDto;
import com.example.userservice.commons.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDto createUser(UserDto userDto) {

    userDto.setUserId(UUID.randomUUID().toString());

    ModelMapper mapper = new ModelMapper();

    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 정확히 맞아야 실행됨

    User user = mapper.map(userDto, User.class);

    user.setEncryptPwd("encrypted_password");

    userRepository.save(user);

    return null;
  }
}
