package com.todo.todo.model.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderDetailsResponse {
  private Long id;
  private String name;
  private List<TaskDetailsResponse> tasks;
}
