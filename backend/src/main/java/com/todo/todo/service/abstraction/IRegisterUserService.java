package com.todo.todo.service.abstraction;

import com.todo.todo.exception.EmailAlredyTakenException;
import com.todo.todo.model.entity.User;
import com.todo.todo.model.request.UserDetailsRequest;

public interface IRegisterUserService {

  User register(UserDetailsRequest registerRequest) throws EmailAlredyTakenException;
}
