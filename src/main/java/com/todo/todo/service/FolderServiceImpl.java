package com.todo.todo.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.todo.todo.exception.FolderAlredyExistException;
import com.todo.todo.model.entity.Folder;
import com.todo.todo.model.request.CreateFolderRequest;
import com.todo.todo.repository.IFolderRepository;
import com.todo.todo.service.abstraction.ICreateFolderService;
import com.todo.todo.service.abstraction.IDeleteFolderService;
import com.todo.todo.service.abstraction.IGetFolderService;
import com.todo.todo.service.abstraction.IListFolderService;

@Service
public class FolderServiceImpl
    implements ICreateFolderService, IGetFolderService, IDeleteFolderService, IListFolderService {

  @Autowired
  private IFolderRepository folderRepository;

  @Override
  @Transactional
  public Folder create(CreateFolderRequest createFolderRequest) throws FolderAlredyExistException {
    if (!folderRepository.findByName(createFolderRequest.getName()).isEmpty()) {
      throw new FolderAlredyExistException(
          "The folder " + createFolderRequest.getName() + " alredy exist.");
    }

    Folder folder = new Folder();
    folder.setName(createFolderRequest.getName());

    return folderRepository.save(folder);
  }

  @Override
  @Transactional(readOnly = true)
  public Folder getBy(long id) throws EntityNotFoundException {
    Optional<Folder> folder = folderRepository.findById(id);

    if (folder.isEmpty()) {
      throw new EntityNotFoundException("Folder not found.");
    }

    return folder.get();
  }

  @Override
  @Transactional
  public void deleteBy(long id) throws EntityNotFoundException {
    Optional<Folder> folder = folderRepository.findById(id);

    if (folder.isEmpty()) {
      throw new EntityNotFoundException("Folder not found.");
    }

    folderRepository.delete(folder.get());
  }

  @Override
  @Transactional(readOnly = true)
  public List<Folder> getAll() {
    return folderRepository.findAll();
  }
}
