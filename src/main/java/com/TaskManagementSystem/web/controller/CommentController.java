package com.TaskManagementSystem.web.controller;

import com.TaskManagementSystem.domain.comment.Comment;
import com.TaskManagementSystem.domain.task.Task;
import com.TaskManagementSystem.service.CommentService;
import com.TaskManagementSystem.service.UserService;
import com.TaskManagementSystem.web.dto.comment.CommentDto;
import com.TaskManagementSystem.web.dto.task.TaskDto;
import com.TaskManagementSystem.web.mappers.CommentMapper;
import com.TaskManagementSystem.web.validation.OnCreate;
import com.TaskManagementSystem.web.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@Tag(name = "Comment Controller", description = "Comment API")
@Validated
public class CommentController {

  private final CommentService commentService;
  private final CommentMapper commentMapper;
  private final UserService userService;

  @PostMapping
  @Operation(summary = "Create comment")
  public CommentDto create(@Validated(OnCreate.class)
  @RequestBody final CommentDto dto, @RequestParam Long taskId) {
    Comment createdComment = commentMapper.toEntity(dto);
    commentService.create(createdComment, taskId, userService.getAuthenticationUserId());
    return commentMapper.toDto(createdComment);
  }

  @PutMapping
  @Operation(summary = "Update comment")
  @PreAuthorize("@customSecurityExpression.canAccessComment(#dto.id)")
  public CommentDto update(@Validated(OnUpdate.class)
  @RequestBody final CommentDto dto) {
    Comment comment = commentMapper.toEntity(dto);
    Comment updatedComment = commentService.update(comment);
    return commentMapper.toDto(updatedComment);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get Comment by id")
  public CommentDto getById(@PathVariable final Long id) {
    Comment comment = commentService.getById(id);
    return commentMapper.toDto(comment);
  }

  @GetMapping("/tasks/{taskId}")
  @Operation(summary = "Get Comments by taskId")
  public List<CommentDto> getByAuthor(@PathVariable final Long taskId,
      @ParameterObject @PageableDefault(sort = {"id"}) Pageable pageable) {
    Page<Comment> comments = commentService.getAllByTaskId(taskId, pageable);
    return commentMapper.toDto(comments.getContent());
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete comment")
  @PreAuthorize("@customSecurityExpression.canAccessComment(#id)")
  public void deleteById(@PathVariable final Long id) {
    commentService.delete(id);
  }

}
