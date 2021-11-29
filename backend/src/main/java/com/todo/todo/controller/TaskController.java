package com.todo.todo.controller;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import com.todo.todo.common.converter.ConverterUtils;
import com.todo.todo.exception.InvalidRequestException;
import com.todo.todo.model.entity.Task;
import com.todo.todo.model.request.CreateTaskRequest;
import com.todo.todo.model.request.UpdateTaskRequest;
import com.todo.todo.model.response.TaskDetailsResponse;
import com.todo.todo.service.abstraction.ICreateTaskService;
import com.todo.todo.service.abstraction.IDeleteTaskService;
import com.todo.todo.service.abstraction.IUpdateTaskService;

@CrossOrigin(origins = "http://localhost:3000",
    methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PATCH})
@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ICreateTaskService createTaskService;

  @Autowired
  private IDeleteTaskService deleteTaskService;

  @Autowired
  private IUpdateTaskService updateTaskService;

  @Autowired
  private ConverterUtils converterUtils;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TaskDetailsResponse> create(
      @RequestBody(required = true) CreateTaskRequest createTaskRequest)
      throws EntityNotFoundException, InvalidRequestException {
    validateCreateRequest(createTaskRequest);

    Task task = createTaskService.create(createTaskRequest);
    return new ResponseEntity<>(converterUtils.toResponse(task), HttpStatus.CREATED);
  }


  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Empty> deleteBy(@PathVariable("id") long id) {
    deleteTaskService.deleteBy(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TaskDetailsResponse> update(@PathVariable("id") long id,
      @RequestBody UpdateTaskRequest updateTaskRequest) {
    Task task = updateTaskService.update(id, updateTaskRequest);
    return new ResponseEntity<>(converterUtils.toResponse(task), HttpStatus.OK);
  }

  private void validateCreateRequest(CreateTaskRequest request) throws InvalidRequestException {
    if (request.getText() == null || request.getText().isBlank()) {
      throw new InvalidRequestException("The attribute text must not be null or empty");
    }
    if (request.getFolderName() == null || request.getFolderName().isBlank()) {
      throw new InvalidRequestException("The attribute folderName must not be null or empty");
    }
  }
}
