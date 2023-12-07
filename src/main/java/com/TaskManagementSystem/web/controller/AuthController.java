package com.TaskManagementSystem.web.controller;


import com.TaskManagementSystem.domain.user.User;
import com.TaskManagementSystem.service.AuthService;
import com.TaskManagementSystem.service.UserService;
import com.TaskManagementSystem.web.dto.auth.JwtRequest;
import com.TaskManagementSystem.web.dto.auth.JwtResponse;
import com.TaskManagementSystem.web.dto.user.UserDto;
import com.TaskManagementSystem.web.mappers.UserMapper;
import com.TaskManagementSystem.web.validation.OnCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {

  private final AuthService authService;
  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping("/login")
  @Operation(summary = "Login user")
  public JwtResponse login(@Validated
  @RequestBody final JwtRequest loginRequest) {
    return authService.login(loginRequest);
  }

  @PostMapping("/register")
  @Operation(summary = "Registration user")
  public UserDto register(@Validated(OnCreate.class)
  @RequestBody final UserDto userDto) {
    User user = userMapper.toEntity(userDto);
    User createdUser = userService.create(user);
    return userMapper.toDto(createdUser);
  }

  @PostMapping("/refresh")
  @Operation(summary = "Update token")
  public JwtResponse refresh(@RequestBody final String refreshToken) {
    return authService.refresh(refreshToken);
  }

}
