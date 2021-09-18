package com.example.userservice.service.impl;

import com.example.userservice.commons.dto.api.response.ResponseOrder;
import com.example.userservice.commons.dto.user.UserDto;
import com.example.userservice.commons.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  public UserDto createUser(UserDto userDto) {

    userDto.setUserId(UUID.randomUUID().toString());

    ModelMapper mapper = new ModelMapper();

    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 정확히 맞아야 실행됨

    User user = mapper.map(userDto, User.class);

    user.setEncryptPwd(passwordEncoder.encode(userDto.getPwd()));

    userRepository.save(user);

    return mapper.map(user, UserDto.class);
  }

  @Override
  public UserDto getUserByUserId(String userId) {

    User user =
        userRepository
            .findByUserId(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User not found."));

    UserDto userDto = new ModelMapper().map(user, UserDto.class);

    List<ResponseOrder> orders = new ArrayList<>();

    userDto.setOrders(orders);

    return userDto;
  }

  @Override
  public List<User> getUserByAll() {
    return userRepository.findAll();
  }
}
