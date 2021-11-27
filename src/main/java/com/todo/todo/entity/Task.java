package com.todo.todo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TASK")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TASK_ID")
  private Long id;

  @Column(name = "TEXT", nullable = false)
  private String text;

  @Column(name = "IS_DONE", nullable = false)
  private Boolean isDone;

  @ManyToOne
  @JoinColumn(name = "FOLDER_ID", nullable = false)
  private Folder folder;
}
