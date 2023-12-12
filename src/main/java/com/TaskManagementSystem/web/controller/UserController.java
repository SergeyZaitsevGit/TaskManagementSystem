package com.TaskManagementSystem.web.controller;

import com.TaskManagementSystem.domain.user.User;
import com.TaskManagementSystem.service.UserService;
import com.TaskManagementSystem.web.dto.user.UserDto;
import com.TaskManagementSystem.web.mappers.UserMapper;
import com.TaskManagementSystem.web.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "User API")
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @PutMapping
  @Operation(summary = "Update user")
  @PreAuthorize("@customSecurityExpression.canAccessUser(#dto.id)")
  public UserDto update(@Validated(OnUpdate.class)
  @RequestBody final UserDto dto) {
    User user = userMapper.toEntity(dto);
    User updatedUser = userService.update(user);
    return userMapper.toDto(updatedUser);
  }


  @GetMapping("/{id}")
  @Operation(summary = "Get UserDto by id")
  @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
  public UserDto getById(@PathVariable final Long id) {
    User user = userService.getById(id);
    return userMapper.toDto(user);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete user by id")
  @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
  public void deleteById(@PathVariable final Long id) {
    userService.delete(id);
  }

}
