package com.TaskManagementSystem.service.impl;

import com.TaskManagementSystem.domain.exception.ResourceNotFoundException;
import com.TaskManagementSystem.domain.task.Priority;
import com.TaskManagementSystem.domain.task.Status;
import com.TaskManagementSystem.domain.task.Task;
import com.TaskManagementSystem.domain.user.User;
import com.TaskManagementSystem.repository.TaskRepository;
import com.TaskManagementSystem.service.TaskService;
import com.TaskManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final UserService userService;

  @Override
  public Task getById(Long id) {
    return taskRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Task not found.")
    );
  }

  @Override
  public Page<Task> getAllByAuthorId(Long id, Pageable pageable) {
    return taskRepository.findTasksByAuthorId(id, pageable);
  }

  @Override
  public Page<Task> getAllByAssigneeIdId(Long id, Pageable pageable) {
    return taskRepository.findTasksByAssigneeId(id, pageable);
  }

  @Override
  public Task update(Task task) {
    Task updated = getById(task.getId());
    if (task.getStatus() == null) {
      updated.setStatus(Status.IN_PROGRESS);
      updated.setPriority(Priority.MEDIUM);
    } else {
      updated.setStatus(task.getStatus());
      updated.setPriority(task.getPriority());
    }
    updated.setTitle(task.getTitle());
    updated.setDescription(task.getDescription());
    taskRepository.save(updated);
    return task;
  }


  @Override
  public Task updateStatus(Long taskId, Status status) {
    Task task = getById(taskId);
    task.setStatus(status);
    taskRepository.save(task);
    return task;
  }

  @Override
  public Task create(Task task, Long authorId, Long assigneeId) {

    User author = userService.getById(authorId);
    User assignee = userService.getById(assigneeId);

    if (task.getStatus() == null) {
      task.setStatus(Status.IN_PROGRESS);
    }
    if (task.getPriority() == null) {
      task.setPriority(Priority.MEDIUM);
    }

    task.setAuthor(author);
    task.setAssignee(assignee);
    taskRepository.save(task);
    return task;
  }

  @Override
  public void delete(Long id) {
    taskRepository.deleteById(id);
  }
}
