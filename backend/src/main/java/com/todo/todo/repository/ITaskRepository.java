package com.todo.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.todo.todo.model.entity.Folder;
import com.todo.todo.model.entity.Task;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {

  @Query(value = "from Folder f where f.name = :name")
  Folder findFolderByName(@Param("name") String name);
}
