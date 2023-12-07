package com.TaskManagementSystem.web.security.expression;

import com.TaskManagementSystem.service.CommentService;
import com.TaskManagementSystem.service.TaskService;
import com.TaskManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor

public class CustomSecurityExpression {

  private final UserService userService;
  private final TaskService taskService;
  private final CommentService commentService;

  public boolean canAccessUser(Long userId) {
    return userId.equals(userService.getAuthenticationUserId());
  }

  public boolean canAccessTaskAuthor(Long taskId) {
    Long userId = userService.getAuthenticationUserId();
    return userService.isTaskOwner(userId, taskId);
  }

  public boolean canAccessTaskAssignee(Long taskId) {
    Long userId = userService.getAuthenticationUserId();
    System.out.println(userId);
    System.out.println(taskId);
    return userService.isTaskAssignee(userId, taskId);
  }

  public boolean canAccessComment(Long commentId) {
    Long userId = userService.getAuthenticationUserId();
    return userService.isCommentOwner(userId, commentId);
  }
}
