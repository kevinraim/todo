package com.todo.todo.service.abstraction;

import javax.persistence.EntityNotFoundException;

public interface IDeleteFolderService {

  void deleteBy(long id) throws EntityNotFoundException;
}
