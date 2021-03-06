package com.example.userservice.commons.security;

import com.example.userservice.commons.dto.api.request.RequestLogin;
import com.example.userservice.commons.dto.user.UserDto;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final UserService userService;
  private final Environment env;

  public AuthenticationFilter(
      UserService userService, Environment env, AuthenticationManager authenticationManager) {

    super(authenticationManager);

    this.userService = userService;
    this.env = env;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    try {
      RequestLogin creds =
          new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

      // spring security 에서 사용가능한 token 으로 변환
      // 매개변수 : email, password, 권한 목록

      // 인증을 처리하는 manager 로 전달
      return getAuthenticationManager()
          .authenticate(
              new UsernamePasswordAuthenticationToken(
                  creds.getEmail(), creds.getPassword(), new ArrayList<>()));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {

    // 인증 성공 후 작업 처리
    String username = ((User) authResult.getPrincipal()).getUsername();

    UserDto userDetails = userService.getUserDetailByEmail(username);

    String token =
        Jwts.builder()
            .setSubject(userDetails.getUserId())
            .setExpiration(
                new Date(
                    System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("token.expiration-time"))))
            .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
            .compact();

    response.addHeader("token", token);
    response.addHeader("userId", userDetails.getUserId());
  }
}
