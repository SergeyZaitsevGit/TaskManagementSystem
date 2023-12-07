package com.TaskManagementSystem.service;

import com.TaskManagementSystem.domain.user.User;

public interface UserService {

  Long getAuthenticationUserId();
  User getById(Long id);

  User getByEmail(String username);

  User update(User user);

  User create(User user);

  void delete(Long id);

  boolean isTaskOwner(Long userId, Long taskId);

  boolean isTaskAssignee(Long userId, Long taskId);

  boolean isCommentOwner(Long userId, Long commentId);

}
