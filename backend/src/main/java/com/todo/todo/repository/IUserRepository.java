package com.todo.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.todo.todo.model.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);
}
