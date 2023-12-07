package com.TaskManagementSystem.domain.task;

import com.TaskManagementSystem.domain.comment.Comment;
import com.TaskManagementSystem.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String description;

  @Enumerated(value = EnumType.STRING)
  private Status status;

  @Enumerated(value = EnumType.STRING)
  private Priority priority;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assignee_id", nullable = false)
  private User assignee;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "task", cascade = CascadeType.ALL)
  List<Comment> commentList;

  @PreRemove private void remove(){
    author.getAuthorTaskList().remove(this);
    assignee.getAssigneeTaskList().remove(this);
  }

}
