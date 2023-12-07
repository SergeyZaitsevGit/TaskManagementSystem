package com.TaskManagementSystem.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JwtResponse {

  private Long id;
  private String email;
  private String accessToken;
  private String refreshToken;

}
