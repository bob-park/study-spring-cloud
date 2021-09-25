package com.example.userservice.controller;

import com.example.userservice.commons.dto.api.request.RequestUser;
import com.example.userservice.commons.dto.api.response.ResponseUser;
import com.example.userservice.commons.dto.user.UserDto;
import com.example.userservice.commons.entity.UserEntity;
import com.example.userservice.commons.vo.Greeting;
import com.example.userservice.service.UserService;
import io.micrometer.core.annotation.Timed;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {

  private final Environment env;
  private final Greeting greeting;

  private final UserService userService;

  public UserController(Environment env, Greeting greeting, UserService userService) {
    this.env = env;
    this.greeting = greeting;
    this.userService = userService;
  }

  @Timed(value = "users.status", longTask = true)
  @GetMapping(path = "health_check")
  public Map<String, Object> status() {

    //    return String.format(
    //        "It's Working in User Service on PORT %s", env.getProperty("local.server.port"));

    Map<String, Object> result = new LinkedHashMap<>();

    result.put("local.server.port", env.getProperty("local.server.port"));
    result.put("server.port", env.getProperty("server.port"));
    result.put("token.secret", env.getProperty("token.secret"));
    result.put("token.expiration-time", env.getProperty("token.expiration-time"));

    return result;
  }

  @Timed(value = "users.welcome", longTask = true)
  @GetMapping(path = "welcome")
  public String welcome() {
    //    return env.getProperty("greeting.message");
    return greeting.getMessage();
  }

  @PostMapping(path = "users")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseUser createUser(@RequestBody RequestUser user) {

    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 정확히 맞아야 실행됨

    UserDto userDto = mapper.map(user, UserDto.class);

    userService.createUser(userDto);

    return mapper.map(userDto, ResponseUser.class);
  }

  @GetMapping(path = "users")
  public List<ResponseUser> getUsers() {
    List<UserEntity> userEntityList = userService.getUserByAll();

    return userEntityList.stream()
        .map(userEntity -> new ModelMapper().map(userEntity, ResponseUser.class))
        .collect(Collectors.toList());
  }

  @GetMapping(path = "users/{userId}")
  public ResponseUser getUser(@PathVariable String userId) {

    UserDto userDto = userService.getUserByUserId(userId);

    return new ModelMapper().map(userDto, ResponseUser.class);
  }
}
