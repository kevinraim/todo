package com.todo.todo.exception;

public class EmailAlredyTakenException extends Exception {

  private static final long serialVersionUID = 1L;

  public EmailAlredyTakenException(String message) {
    super(message);
  }

}
