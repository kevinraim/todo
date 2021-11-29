package com.todo.todo.model.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FOLDER")
public class Folder {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "FOLDER_ID")
  private Long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "folder")
  private List<Task> tasks;

}
