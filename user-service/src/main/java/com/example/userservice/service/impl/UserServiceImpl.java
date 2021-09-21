package com.example.userservice.service.impl;

import com.example.userservice.commons.dto.api.response.ResponseOrder;
import com.example.userservice.commons.dto.user.UserDto;
import com.example.userservice.commons.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

    UserEntity userEntity = mapper.map(userDto, UserEntity.class);

    userEntity.setEncryptPwd(passwordEncoder.encode(userDto.getPwd()));

    userRepository.save(userEntity);

    return mapper.map(userEntity, UserDto.class);
  }

  @Override
  public UserDto getUserByUserId(String userId) {

    UserEntity userEntity =
        userRepository
            .findByUserId(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User not found."));

    UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

    List<ResponseOrder> orders = new ArrayList<>();

    userDto.setOrders(orders);

    return userDto;
  }

  @Override
  public List<UserEntity> getUserByAll() {
    return userRepository.findAll();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserEntity userEntity =
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

    return new User(
        userEntity.getEmail(),
        userEntity.getEncryptPwd(),
        true,
        true,
        true,
        true,
        new ArrayList<>());
  }
}
