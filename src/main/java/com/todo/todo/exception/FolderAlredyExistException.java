package com.todo.todo.exception;

public class FolderAlredyExistException extends Exception {

  private static final long serialVersionUID = 1L;

  public FolderAlredyExistException(String message) {
    super(message);
  }
}
