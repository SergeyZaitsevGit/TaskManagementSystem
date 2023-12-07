package com.TaskManagementSystem.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtRequest {

  @Schema(description = "email", example = "kantrast404@mail.ru")
  @NotNull(message = "Email must be not null.")
  private String email;

  @Schema(description = "password", example = "1")
  @NotNull(message = "Password must be not null.")
  private String password;

}
