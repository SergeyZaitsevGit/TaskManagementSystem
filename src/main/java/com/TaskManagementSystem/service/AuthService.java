package com.TaskManagementSystem.service;

import com.TaskManagementSystem.web.dto.auth.JwtRequest;
import com.TaskManagementSystem.web.dto.auth.JwtResponse;

public interface AuthService {
  JwtResponse login(JwtRequest loginRequest);
  JwtResponse refresh(String refreshToken);
}
