package com.todo.todo.controller;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import com.todo.todo.common.converter.ConverterUtils;
import com.todo.todo.exception.FolderAlredyExistException;
import com.todo.todo.exception.InvalidRequestException;
import com.todo.todo.model.entity.Folder;
import com.todo.todo.model.request.CreateFolderRequest;
import com.todo.todo.model.response.FolderDetailsResponse;
import com.todo.todo.model.response.ListFoldersResponse;
import com.todo.todo.service.abstraction.ICreateFolderService;
import com.todo.todo.service.abstraction.IDeleteFolderService;
import com.todo.todo.service.abstraction.IGetFolderService;
import com.todo.todo.service.abstraction.IListFolderService;

@CrossOrigin(origins = "http://localhost:3000",
    methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST})
@RestController
@RequestMapping("/folders")
public class FolderController {

  @Autowired
  private ICreateFolderService createFolderService;

  @Autowired
  private IGetFolderService getFolderService;

  @Autowired
  private IListFolderService listFolderService;

  @Autowired
  private IDeleteFolderService deleteFolderService;

  @Autowired
  private ConverterUtils converterUtils;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FolderDetailsResponse> create(
      @RequestBody(required = true) CreateFolderRequest createFolderRequest)
      throws FolderAlredyExistException, InvalidRequestException {
    if (createFolderRequest.getName() == null || createFolderRequest.getName().isBlank()) {
      throw new InvalidRequestException("The atributte name must not be empty or null");
    }

    Folder folder = createFolderService.create(createFolderRequest);
    return new ResponseEntity<>(converterUtils.toResponse(folder), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FolderDetailsResponse> getBy(@PathVariable("id") long id)
      throws EntityNotFoundException {
    Folder folder = getFolderService.getBy(id);
    return new ResponseEntity<>(converterUtils.toResponse(folder), HttpStatus.OK);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListFoldersResponse> getAll() {
    List<Folder> folders = listFolderService.getAll();
    return new ResponseEntity<>(converterUtils.toResponse(folders), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Empty> deleteBy(@PathVariable("id") long id) {
    deleteFolderService.deleteBy(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
