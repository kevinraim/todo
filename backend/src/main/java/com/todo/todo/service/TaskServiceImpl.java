package com.todo.todo.service;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.todo.todo.model.entity.Folder;
import com.todo.todo.model.entity.Task;
import com.todo.todo.model.request.CreateTaskRequest;
import com.todo.todo.model.request.UpdateTaskRequest;
import com.todo.todo.repository.ITaskRepository;
import com.todo.todo.service.abstraction.ICreateTaskService;
import com.todo.todo.service.abstraction.IDeleteTaskService;
import com.todo.todo.service.abstraction.IUpdateTaskService;

@Service
public class TaskServiceImpl implements ICreateTaskService, IDeleteTaskService, IUpdateTaskService {

  @Autowired
  private ITaskRepository taskRepository;

  @Override
  @Transactional
  public Task create(CreateTaskRequest createTaskRequest) throws EntityNotFoundException {
    Folder taskFolder = taskRepository.findFolderByName(createTaskRequest.getFolderName());

    if (taskFolder == null) {
      throw new EntityNotFoundException("Not found");
    }

    Task task = new Task();
    task.setText(createTaskRequest.getText());
    task.setIsDone(false);
    task.setFolder(taskFolder);
    return taskRepository.save(task);
  }

  @Override
  @Transactional
  public void deleteBy(Long id) throws EntityNotFoundException {
    Optional<Task> task = taskRepository.findById(id);

    if (task.isEmpty()) {
      throw new EntityNotFoundException("Task not found.");
    }

    taskRepository.delete(task.get());
  }

  @Override
  @Transactional
  public Task update(Long id, UpdateTaskRequest updateTaskRequest) throws EntityNotFoundException {
    Optional<Task> task = taskRepository.findById(id);

    if (task.isEmpty()) {
      throw new EntityNotFoundException("Task not found.");
    }
    if (updateTaskRequest.getText() != null && !updateTaskRequest.getText().isBlank()) {
      task.get().setText(updateTaskRequest.getText());
    }
    if (updateTaskRequest.getIsDone() != null) {
      task.get().setIsDone(updateTaskRequest.getIsDone());
    }

    return taskRepository.save(task.get());
  }
}
