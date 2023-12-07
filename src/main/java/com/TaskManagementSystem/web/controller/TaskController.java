package com.TaskManagementSystem.web.controller;

import com.TaskManagementSystem.domain.task.Status;
import com.TaskManagementSystem.domain.task.Task;
import com.TaskManagementSystem.service.TaskService;
import com.TaskManagementSystem.service.UserService;
import com.TaskManagementSystem.web.dto.task.TaskDto;
import com.TaskManagementSystem.web.mappers.TaskMapper;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Controller", description = "Task API")
@Validated
public class TaskController {

  private final TaskService taskService;
  private final TaskMapper taskMapper;
  private final UserService userService;

  @PostMapping
  @Operation(summary = "Create task")
  public TaskDto create(@Validated(OnCreate.class)
  @RequestBody final TaskDto dto, @RequestParam Long assigneeId) {
    Task task = taskMapper.toEntity(dto);
    Task createdTask = taskService.create(task, userService.getAuthenticationUserId(), assigneeId);
    return taskMapper.toDto(createdTask);
  }

  @PutMapping
  @Operation(summary = "Update task")
  @PreAuthorize("@customSecurityExpression.canAccessTaskAuthor(#dto.id)")
  public TaskDto update(@Validated(OnUpdate.class)
  @RequestBody final TaskDto dto) {
    Task task = taskMapper.toEntity(dto);
    Task updatedTask = taskService.update(task);
    return taskMapper.toDto(updatedTask);
  }

  @PatchMapping("/status/{idTask}")
  @Operation(summary = "Update task status")
  @PreAuthorize("@customSecurityExpression.canAccessTaskAssignee(#idTask)")
  public TaskDto updateStatus(
      @RequestBody final Status status, @PathVariable final Long idTask) {
    Task updatedTask = taskService.updateStatus(idTask, status);
    return taskMapper.toDto(updatedTask);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get TaskDto by id")
  public TaskDto getById(@PathVariable final Long id) {
    Task task = taskService.getById(id);
    return taskMapper.toDto(task);
  }

  @GetMapping("/author/{userId}")
  @Operation(summary = "Get TasksDto by AuthorId")
  @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
  public List<TaskDto> getByAuthor(@PathVariable final Long userId,
      @ParameterObject @PageableDefault(sort = {"id"}) Pageable pageable) {
    Page<Task> tasks = taskService.getAllByAuthorId(userId, pageable);
    return taskMapper.toDto(tasks.getContent());
  }

  @GetMapping("/assignee/{userId}")
  @Operation(summary = "Get TasksDto by AssigneeId")
  @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
  public List<TaskDto> getByAssignee(@PathVariable final Long userId,
      @ParameterObject @PageableDefault(sort = {"id"}) Pageable pageable) {
    Page<Task> tasks = taskService.getAllByAssigneeIdId(userId, pageable);
    return taskMapper.toDto(tasks.getContent());
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete task")
  @PreAuthorize("@customSecurityExpression.canAccessTaskAuthor(#id)")
  public void deleteById(@PathVariable final Long id) {
    taskService.delete(id);
  }
}
