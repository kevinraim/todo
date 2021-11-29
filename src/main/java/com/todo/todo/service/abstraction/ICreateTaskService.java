package com.todo.todo.service.abstraction;

import javax.persistence.EntityNotFoundException;
import com.todo.todo.model.entity.Task;
import com.todo.todo.model.request.CreateTaskRequest;

public interface ICreateTaskService {

  Task create(CreateTaskRequest createTaskRequest) throws EntityNotFoundException;
}
