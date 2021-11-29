package com.todo.todo.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDetailsResponse {
  private Long id;
  private String text;
  private Boolean isDone;

  @JsonInclude(Include.NON_NULL)
  private String folder;
}
