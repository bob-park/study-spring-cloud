package com.example.userservice.controller;

import com.example.userservice.commons.dto.api.request.RequestUser;
import com.example.userservice.commons.dto.user.UserDto;
import com.example.userservice.commons.vo.Greeting;
import com.example.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

  private final Environment env;
  private final Greeting greeting;

  private final UserService userService;

  public UserController(Environment env, Greeting greeting, UserService userService) {
    this.env = env;
    this.greeting = greeting;
    this.userService = userService;
  }

  @GetMapping(path = "health_check")
  public String status() {
    return "It's Working in User Service";
  }

  @GetMapping(path = "welcome")
  public String welcome() {
    //    return env.getProperty("greeting.message");
    return greeting.getMessage();
  }

  @PostMapping(path = "users")
  @ResponseStatus(HttpStatus.CREATED)
  public String createUser(@RequestBody RequestUser user) {

    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 정확히 맞아야 실행됨

    UserDto userDto = mapper.map(user, UserDto.class);

    userService.createUser(userDto);

    return "Create user method is called.";
  }
}
