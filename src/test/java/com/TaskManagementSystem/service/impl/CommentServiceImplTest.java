package com.TaskManagementSystem.service.impl;

import com.TaskManagementSystem.domain.comment.Comment;
import com.TaskManagementSystem.domain.exception.ResourceNotFoundException;
import com.TaskManagementSystem.domain.task.Task;
import com.TaskManagementSystem.domain.user.User;
import com.TaskManagementSystem.repository.CommentRepository;
import com.TaskManagementSystem.service.TaskService;
import com.TaskManagementSystem.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceImplTest {

  @Mock
  private CommentRepository commentRepository;

  @Mock
  private TaskService taskService;

  @Mock
  private UserService userService;

  @InjectMocks
  private CommentServiceImpl commentService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetById() {
    Long commentId = 1L;
    Comment comment = new Comment();
    when(commentRepository.findById(commentId)).thenReturn(java.util.Optional.of(comment));

    Comment result = commentService.getById(commentId);
    assertEquals(comment, result);
  }

@Test
void testGetAllByTaskId() {
    Pageable pageable = Pageable.unpaged();
    Page<Comment> mockedPage = mock(Page.class);
    when(commentRepository.findCommentsByTaskId(1L, pageable)).thenReturn(mockedPage);

    Page<Comment> result = commentService.getAllByTaskId(1L, pageable);
    assertSame(mockedPage, result);
    }

@Test
void testUpdate() {
    Comment updated = new Comment();
    updated.setId(1L);
    Comment comment = new Comment();
    comment.setId(1L);
    when(commentRepository.existsById(comment.getId())).thenReturn(true);
    when(commentRepository.save(comment)).thenReturn(comment);
    when(commentRepository.findById(1L)).thenReturn(Optional.of(updated));

    Comment result = commentService.update(comment);
    assertEquals(comment, result);
    }

@Test
void testCreate() {
    Task task = new Task();
    User user = new User();
    Comment comment = new Comment();
    when(taskService.getById(1L)).thenReturn(task);
    when(userService.getById(1L)).thenReturn(user);

    when(commentRepository.save(comment)).thenReturn(comment);

    Comment result = commentService.create(comment, 1L, 1L);
    assertEquals(comment, result);
    }

@Test
void testDelete() {
  commentService.delete(1L);
  verify(commentRepository, times(1)).deleteById(1L);
}
}