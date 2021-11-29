package com.todo.todo.service.abstraction;

import com.todo.todo.exception.FolderAlredyExistException;
import com.todo.todo.model.entity.Folder;
import com.todo.todo.model.request.CreateFolderRequest;

public interface ICreateFolderService {
  Folder create(CreateFolderRequest createFolderRequest) throws FolderAlredyExistException;
}
