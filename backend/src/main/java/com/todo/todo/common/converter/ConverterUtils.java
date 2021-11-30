package com.todo.todo.common.converter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.todo.todo.model.entity.Folder;
import com.todo.todo.model.entity.Task;
import com.todo.todo.model.response.FolderDetailsResponse;
import com.todo.todo.model.response.ListFoldersResponse;
import com.todo.todo.model.response.TaskDetailsResponse;
import com.todo.todo.model.response.UserDetailsResponse;

@Component("converter")
public class ConverterUtils {

  public TaskDetailsResponse toResponse(Task task) {
    TaskDetailsResponse taskDetailsResponse = new TaskDetailsResponse();
    taskDetailsResponse.setId(task.getId());
    taskDetailsResponse.setText(task.getText());
    taskDetailsResponse.setIsDone(task.getIsDone());
    taskDetailsResponse.setFolder(task.getFolder().getName());
    return taskDetailsResponse;
  }

  public FolderDetailsResponse toResponse(Folder folder) {
    FolderDetailsResponse folderDetailsResponse = new FolderDetailsResponse();
    folderDetailsResponse.setId(folder.getId());
    folderDetailsResponse.setName(folder.getName());

    List<TaskDetailsResponse> tasksResponse = new ArrayList<>();

    List<Task> tasks = folder.getTasks();
    if (tasks != null) {
      for (Task task : tasks) {
        tasksResponse.add(folderTaskToResponse(task));
      }
    }
    folderDetailsResponse.setTasks(tasksResponse);

    return folderDetailsResponse;
  }

  public ListFoldersResponse toResponse(List<Folder> folders) {
    List<FolderDetailsResponse> foldersDetails = new ArrayList<>();

    for (Folder folder : folders) {
      foldersDetails.add(toResponse(folder));
    }

    return new ListFoldersResponse(foldersDetails);
  }

  public UserDetailsResponse toResponse(String email, String jwtToken) {
    UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
    userDetailsResponse.setEmail(email);
    userDetailsResponse.setJwtToken(jwtToken);
    return userDetailsResponse;
  }

  private TaskDetailsResponse folderTaskToResponse(Task task) {
    TaskDetailsResponse taskDetailsResponse = toResponse(task);
    taskDetailsResponse.setFolder(null);
    return taskDetailsResponse;
  }
}
