package com.todo.todo.exception;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.todo.todo.model.response.ErrorResponse;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(HttpServletRequest request,
      EntityNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(e, HttpStatus.NOT_FOUND.value()));
  }

  @ExceptionHandler(FolderAlredyExistException.class)
  public ResponseEntity<ErrorResponse> handleFolderAlredyExistException(HttpServletRequest request,
      FolderAlredyExistException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(e, HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler(InvalidRequestException.class)
  public ResponseEntity<ErrorResponse> handleInvalidRequestException(HttpServletRequest request,
      InvalidRequestException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(e, HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler(EmailAlredyTakenException.class)
  public ResponseEntity<ErrorResponse> handleEmailAlredyTakenException(HttpServletRequest request,
      EmailAlredyTakenException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(e, HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleDefaultException(HttpServletRequest request,
      Exception e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(e, HttpStatus.BAD_REQUEST.value()));
  }
}
