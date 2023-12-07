package com.TaskManagementSystem.repository;

import com.TaskManagementSystem.domain.task.Task;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
  Page<Task> findTasksByAuthorId(Long id, Pageable pageable);
  Page<Task> findTasksByAssigneeId(Long id, Pageable pageable);
}
