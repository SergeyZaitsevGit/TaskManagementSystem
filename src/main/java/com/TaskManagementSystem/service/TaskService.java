package com.TaskManagementSystem.service;

import com.TaskManagementSystem.domain.task.Status;
import com.TaskManagementSystem.domain.task.Task;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
  Task getById(Long id);

  Page<Task> getAllByAuthorId(Long id, Pageable pageable);

  Page<Task> getAllByAssigneeIdId(Long id, Pageable pageable);

  Task update(Task task);

  Task updateStatus(Long taskId, Status status);

  Task create(Task task, Long authorId,  Long assigneeId);

  void delete(Long id);


}
