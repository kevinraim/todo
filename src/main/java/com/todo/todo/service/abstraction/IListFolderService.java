package com.todo.todo.service.abstraction;

import java.util.List;
import com.todo.todo.model.entity.Folder;

public interface IListFolderService {

  List<Folder> getAll();
}
