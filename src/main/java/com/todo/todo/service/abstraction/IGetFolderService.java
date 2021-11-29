package com.todo.todo.service.abstraction;

import javax.persistence.EntityNotFoundException;
import com.todo.todo.model.entity.Folder;

public interface IGetFolderService {

  Folder getBy(long id) throws EntityNotFoundException;
}
