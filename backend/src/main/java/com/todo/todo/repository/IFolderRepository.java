package com.todo.todo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.todo.todo.model.entity.Folder;

@Repository
public interface IFolderRepository extends JpaRepository<Folder, Long> {

  public Optional<Folder> findByName(String name);
}
