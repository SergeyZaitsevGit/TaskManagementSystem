package com.TaskManagementSystem.service.impl;

import com.TaskManagementSystem.domain.comment.Comment;
import com.TaskManagementSystem.domain.exception.ResourceNotFoundException;
import com.TaskManagementSystem.domain.task.Task;
import com.TaskManagementSystem.domain.user.User;
import com.TaskManagementSystem.repository.CommentRepository;
import com.TaskManagementSystem.service.CommentService;
import com.TaskManagementSystem.service.TaskService;
import com.TaskManagementSystem.service.UserService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final TaskService taskService;
  private final UserService userService;

  @Override
  public Comment getById(Long id) {
    return commentRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Comment not found.")
    );
  }

  @Override
  public Page<Comment> getAllByTaskId(Long id, Pageable pageable) {
    return commentRepository.findCommentsByTaskId(id, pageable);
  }

  @Override
  public Comment update(Comment comment) {
    Comment updated = getById(comment.getId());
    if (!commentRepository.existsById(comment.getId())) {
      throw new ResourceNotFoundException("Comment not found.");
    }
    updated.setText(comment.getText());
    commentRepository.save(updated);
    return updated;
  }

  @Override
  public Comment create(Comment comment, Long taskId, Long userId) {
    Task task = taskService.getById(taskId);
    User user = userService.getById(userId);
    comment.setTask(task);
    comment.setDateCreated(LocalDateTime.now());
    comment.setAuthor(user);
    commentRepository.save(comment);
    return comment;
  }

  @Override
  public void delete(Long id) {
    commentRepository.deleteById(id);
  }
}
