package com.todo.todo.service.abstraction;

import javax.persistence.EntityNotFoundException;
import com.todo.todo.model.entity.Task;
import com.todo.todo.model.request.UpdateTaskRequest;

public interface IUpdateTaskService {

  Task update(Long id, UpdateTaskRequest updateTaskRequest) throws EntityNotFoundException;
}
