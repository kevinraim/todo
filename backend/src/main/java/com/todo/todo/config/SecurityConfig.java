package com.todo.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.todo.todo.common.jwt.JwtFilter;
import com.todo.todo.service.UserServiceImpl;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserServiceImpl userDetails;

  @Autowired
  private JwtFilter jwtFilter;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity security) throws Exception {
    security.cors().and().csrf().disable().authorizeRequests().antMatchers("/auth/**").permitAll()
        .antMatchers(HttpMethod.GET, "/folders/**").hasAnyRole("USER")
        .antMatchers(HttpMethod.POST, "/folders/**").hasAnyRole("USER")
        .antMatchers(HttpMethod.DELETE, "/folders/**").hasAnyRole("USER")
        .antMatchers(HttpMethod.POST, "/tasks/**").hasAnyRole("USER")
        .antMatchers(HttpMethod.PATCH, "/tasks/**").hasAnyRole("USER").and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
