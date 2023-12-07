package com.TaskManagementSystem.service;

import com.TaskManagementSystem.domain.comment.Comment;
import com.TaskManagementSystem.domain.task.Task;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
  Comment getById(Long id);

  Page<Comment> getAllByTaskId(Long id,Pageable pageable);

  Comment update(Comment comment);

  Comment create(Comment comment, Long taskId, Long userId);

  void delete(Long id);
}
