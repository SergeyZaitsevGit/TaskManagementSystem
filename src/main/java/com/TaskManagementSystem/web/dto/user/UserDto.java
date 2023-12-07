package com.TaskManagementSystem.web.dto.user;

import com.TaskManagementSystem.web.validation.OnCreate;
import com.TaskManagementSystem.web.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {
  @Schema(description = "User Id", example = "1")
  @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
  private Long id;

  @Schema(description = "User email", example = "kantrast404@mail.ru")
  @NotNull(message = "Email must be not null.", groups = {OnCreate.class, OnUpdate.class})
  @Length(max = 255, message = "Email cannot be longer than 255 characters.", groups = {
      OnCreate.class, OnUpdate.class})
  private String email;

  @Schema(description = "User password", example = "1")
  @NotNull(message = "Password must be not null.", groups = {OnCreate.class, OnUpdate.class})
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

}
