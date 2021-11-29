package com.todo.todo.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

  private String message;
  private Integer status;

  public ErrorResponse(Exception e, int status) {
    message = e.getMessage();
    this.status = status;
  }
}
