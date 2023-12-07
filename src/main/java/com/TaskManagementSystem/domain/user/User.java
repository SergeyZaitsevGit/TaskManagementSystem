package com.TaskManagementSystem.domain.user;

import com.TaskManagementSystem.domain.comment.Comment;
import com.TaskManagementSystem.domain.task.Task;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Set;
import lombok.Data;


@Table(name = "users")
@Entity
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String password;

  @Column(name = "role")
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "users_roles")
  @Enumerated(value = EnumType.STRING)
  Set<Role> roles;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL)
  List<Task> authorTaskList;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignee", cascade = CascadeType.ALL)
  List<Task> assigneeTaskList;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL)
  List<Comment> commentList;

}
