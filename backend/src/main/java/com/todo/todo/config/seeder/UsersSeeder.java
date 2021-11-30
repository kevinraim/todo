package com.todo.todo.config.seeder;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.todo.todo.model.entity.User;
import com.todo.todo.repository.IUserRepository;

@Component
public class UsersSeeder implements CommandLineRunner {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private final static String USER_EMAIL = "admin@admin.com";
  private final static String USER_PASSWORD = "admin";

  @Override
  public void run(String... args) throws Exception {
    loadUser();
  }

  private void loadUser() {
    if (userRepository.findByEmail(USER_EMAIL) == null) {
      String passwordEncripted = bCryptPasswordEncoder.encode(USER_PASSWORD);
      User user = new User();
      user.setEmail(USER_EMAIL);
      user.setPassword(passwordEncripted);
      userRepository.save(user);
    }
  }

}
