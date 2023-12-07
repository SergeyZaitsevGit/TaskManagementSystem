package com.TaskManagementSystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.TaskManagementSystem.domain.exception.ResourceNotFoundException;
import com.TaskManagementSystem.domain.task.Status;
import com.TaskManagementSystem.domain.task.Task;
import com.TaskManagementSystem.domain.user.User;
import com.TaskManagementSystem.repository.TaskRepository;
import com.TaskManagementSystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TaskServiceImplTest {

  @InjectMocks
  private TaskServiceImpl taskService;

  @Mock
  private TaskRepository taskRepository;

  @Mock
  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetById_ReturnsTask() {
    Long id = 1L;
    Task expectedTask = new Task();
    when(taskRepository.findById(id)).thenReturn(java.util.Optional.of(expectedTask));

    Task actualTask = taskService.getById(id);
    assertEquals(expectedTask, actualTask);
    verify(taskRepository, times(1)).findById(id);
  }

  @Test
  void testGetById_ThrowsException() {
    Long id = 1L;
    when(taskRepository.findById(id)).thenReturn(java.util.Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> taskService.getById(id));
  }

  @Test
  void testUpdateStatus() {
    Task existingTask = new Task();
    existingTask.setId(1L);
    existingTask.setTitle("Existing Title");
    existingTask.setDescription("Existing Description");
    existingTask.setStatus(Status.IN_PROGRESS);

    when(taskRepository.findById(1L)).thenReturn(java.util.Optional.of(existingTask));

    Task result = taskService.updateStatus(1L, Status.COMPLETED);

    assertEquals(Status.COMPLETED, existingTask.getStatus());
    verify(taskRepository, times(1)).findById(1L);
    verify(taskRepository, times(1)).save(existingTask);
    assertEquals(existingTask, result);
  }

  @Test
  void testCreate() {
    Task newTask = new Task();
    newTask.setId(null);
    newTask.setTitle("New Title");
    newTask.setDescription("New Description");

    when(userService.getById(1L)).thenReturn(new User());
    when(userService.getById(2L)).thenReturn(new User());

    Task result = taskService.create(newTask, 1L, 2L);

    verify(taskRepository, times(1)).save(newTask);
    assertEquals(newTask, result);
  }

  @Test
  void testDelete() {
    when(taskRepository.existsById(1L)).thenReturn(true);

    taskService.delete(1L);

    verify(taskRepository, times(1)).deleteById(1L);
  }
}
