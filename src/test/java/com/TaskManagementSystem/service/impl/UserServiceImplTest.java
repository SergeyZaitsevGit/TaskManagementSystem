package com.TaskManagementSystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.TaskManagementSystem.domain.user.User;
import com.TaskManagementSystem.repository.UserRepository;
import com.TaskManagementSystem.service.CommentService;
import com.TaskManagementSystem.service.TaskService;
import com.TaskManagementSystem.web.security.JwtEntity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private TaskService taskService;

  @Mock
  private CommentService commentService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAuthenticationUserId() {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    JwtEntity jwtEntity = new JwtEntity(123L, "ex@mail.ru", "1", null);
    when(authentication.getPrincipal()).thenReturn(jwtEntity);

    Long userId = userService.getAuthenticationUserId();
    assertEquals(123L, userId);
  }

  @Test
  void testGetById() {
    User user = new User();
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    User result = userService.getById(1L);
    assertSame(user, result);
  }

  @Test
  void testGetByEmail() {
    User user = new User();
    when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.of(user));

    User result = userService.getByEmail("test@example.com");
    assertSame(user, result);
  }

  @Test
  void testUpdate() {
    User user = new User();
    user.setPassword("password");
    when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
    when(userRepository.save(user)).thenReturn(user);  // тестовый метод сохранения

    User result = userService.update(user);
    assertEquals("encodedPassword", result.getPassword());
  }

  @Test
  void testCreate() {
    User user = new User();
    user.setEmail("test@example.com");
    user.setPassword("password");
    when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.empty());
    when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
    when(userRepository.save(user)).thenReturn(user);

    User result = userService.create(user);
    assertEquals("encodedPassword", result.getPassword());
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void testDelete() {
    userService.delete(1L);
    verify(userRepository, times(1)).deleteById(1L);
  }

}