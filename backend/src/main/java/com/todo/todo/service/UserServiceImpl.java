package com.todo.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.todo.todo.exception.EmailAlredyTakenException;
import com.todo.todo.model.entity.User;
import com.todo.todo.model.request.UserDetailsRequest;
import com.todo.todo.repository.IUserRepository;
import com.todo.todo.service.abstraction.IRegisterUserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService, IRegisterUserService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    UserBuilder builder = null;

    if (user != null) {
      builder = org.springframework.security.core.userdetails.User.withUsername(username);
      builder.disabled(false);
      builder.password(user.getPassword());
      builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
    } else {
      throw new UsernameNotFoundException("Not Found User");
    }
    return builder.build();
  }

  @Override
  public User register(UserDetailsRequest registerRequest) throws EmailAlredyTakenException {
    if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
      throw new EmailAlredyTakenException("Email alredy taken");
    }

    User user = new User();
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

    return userRepository.save(user);
  }

}
