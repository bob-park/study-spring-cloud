package com.example.userservice.commons.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();

    http.authorizeRequests().antMatchers("/users/**").permitAll();

    // h2-console 을 이용할 경우 반드시 적용해야함
    // frame 으로 구성되어있는 경우 인증때문에 정상적으로 동작안함
    http.headers().frameOptions().disable();
  }
}
