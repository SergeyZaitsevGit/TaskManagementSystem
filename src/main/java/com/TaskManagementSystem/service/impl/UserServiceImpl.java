package com.TaskManagementSystem.service.impl;

import com.TaskManagementSystem.domain.comment.Comment;
import com.TaskManagementSystem.domain.exception.ResourceNotFoundException;
import com.TaskManagementSystem.domain.task.Task;
import com.TaskManagementSystem.domain.user.Role;
import com.TaskManagementSystem.domain.user.User;
import com.TaskManagementSystem.repository.UserRepository;
import com.TaskManagementSystem.service.CommentService;
import com.TaskManagementSystem.service.TaskService;
import com.TaskManagementSystem.service.UserService;
import com.TaskManagementSystem.web.security.JwtEntity;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final TaskService taskService;
  private final CommentService commentService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Long getAuthenticationUserId() {
    Authentication authentication = SecurityContextHolder.getContext()
        .getAuthentication();
    JwtEntity user = (JwtEntity) authentication.getPrincipal();

    return user.getId();
  }

  @Override
  public User getById(Long id) {
    return userRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("User not found.")
    );
  }

  @Override
  public User getByEmail(String email) {
    return userRepository.findUserByEmail(email).orElseThrow(
        () -> new ResourceNotFoundException("User not found.")
    );
  }

  @Override
  public boolean isTaskOwner(Long userId, Long taskId) {
    User user = getById(userId);
    Task task = taskService.getById(taskId);
    return user.getAuthorTaskList().contains(task);
  }

  @Override
  public boolean isTaskAssignee(Long userId, Long taskId) {
    User user = getById(userId);
    Task task = taskService.getById(taskId);
    return user.getAssigneeTaskList().contains(task);
  }

  @Override
  public boolean isCommentOwner(Long userId, Long commentId) {
    User user = getById(userId);
    Comment comment = commentService.getById(commentId);
    return user.getCommentList().contains(comment);
  }

  @Override
  public User update(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return user;
  }

  @Override
  public User create(User user) {
    if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
      throw new IllegalStateException("User already exists.");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Set<Role> roles = Set.of(Role.ROLE_USER);
    user.setRoles(roles);
    userRepository.save(user);
    return user;
  }

  @Override
  public void delete(Long id) {
    userRepository.deleteById(id);
  }
}
