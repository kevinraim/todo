package com.todo.todo.service.abstraction;

import javax.persistence.EntityNotFoundException;

public interface IDeleteTaskService {

  void deleteBy(Long id) throws EntityNotFoundException;
}
