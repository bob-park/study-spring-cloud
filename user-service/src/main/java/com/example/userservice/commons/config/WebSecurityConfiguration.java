package com.example.userservice.commons.config;

import com.example.userservice.commons.security.AuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();

    //    http.authorizeRequests().antMatchers("/users/**").permitAll();

    http.authorizeRequests()
        .antMatchers("/**")
        .hasIpAddress("192.168.0.31") // 해당 IP 가 들어있는 경우만 처리
        .and()
        .addFilter(getAuthenticationFilter()); // 필터 추가

    // h2-console 을 이용할 경우 반드시 적용해야함
    // frame 으로 구성되어있는 경우 인증때문에 정상적으로 동작안함
    http.headers().frameOptions().disable();
  }

  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    AuthenticationFilter authenticationFilter = new AuthenticationFilter();

    authenticationFilter.setAuthenticationManager(authenticationManager());

    return authenticationFilter;
  }
}
