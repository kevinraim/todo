package com.todo.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.todo.todo.common.converter.ConverterUtils;
import com.todo.todo.common.jwt.JwtUtil;
import com.todo.todo.common.validation.EmailValidator;
import com.todo.todo.exception.EmailAlredyTakenException;
import com.todo.todo.exception.InvalidRequestException;
import com.todo.todo.model.entity.User;
import com.todo.todo.model.request.UserDetailsRequest;
import com.todo.todo.model.response.UserDetailsResponse;
import com.todo.todo.service.UserServiceImpl;

@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST})
@RestController
@RequestMapping("/auth")
public class UserController {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private UserServiceImpl userServiceImpl;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private ConverterUtils converterUtils;

  @PostMapping(value = "/log_in")
  public ResponseEntity<UserDetailsResponse> createAuthenticationToken(
      @RequestBody(required = true) UserDetailsRequest loginUserRequest)
      throws InvalidRequestException {
    validateUserRequest(loginUserRequest);

    authManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserRequest.getEmail(),
        loginUserRequest.getPassword()));

    UserDetails userDetails = userServiceImpl.loadUserByUsername(loginUserRequest.getEmail());

    String jwtToken = jwtUtil.generateToken(userDetails);

    return new ResponseEntity<>(converterUtils.toResponse(userDetails.getUsername(), jwtToken),
        HttpStatus.OK);
  }

  @PostMapping(value = "/sign_up")
  public ResponseEntity<UserDetailsResponse> register(
      @RequestBody(required = true) UserDetailsRequest registerUserRequest)
      throws InvalidRequestException, EmailAlredyTakenException {
    validateUserRequest(registerUserRequest);

    User registerUser = userServiceImpl.register(registerUserRequest);
    UserDetails userDetails = userServiceImpl.loadUserByUsername(registerUser.getEmail());

    String jwtToken = jwtUtil.generateToken(userDetails);

    return new ResponseEntity<>(converterUtils.toResponse(registerUser.getEmail(), jwtToken),
        HttpStatus.CREATED);
  }


  private void validateUserRequest(UserDetailsRequest userDetailsRequest)
      throws InvalidRequestException {
    if (userDetailsRequest.getEmail() == null || userDetailsRequest.getEmail().isBlank()) {
      throw new InvalidRequestException("The attribute email must not be null or empty");
    }

    if (!EmailValidator.isValid(userDetailsRequest.getEmail())) {
      throw new InvalidRequestException("Invalid email");
    }

    if (userDetailsRequest.getPassword() == null || userDetailsRequest.getEmail().isBlank()) {
      throw new InvalidRequestException("The attribute password must not be null or empty");
    }
  }

}
