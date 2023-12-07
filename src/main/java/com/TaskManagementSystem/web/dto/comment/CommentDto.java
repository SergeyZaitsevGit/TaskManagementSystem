package com.TaskManagementSystem.web.dto.comment;

import com.TaskManagementSystem.web.validation.OnCreate;
import com.TaskManagementSystem.web.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
public class CommentDto {

  @Schema(description = "Comment Id", example = "1")
  @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
  @Null(message = "Id must be null.", groups = OnCreate.class)
  private Long id;

  @Schema(description = "Comment test", example = "Comment text")
  @NotNull(message = "Text must be not null.", groups = {OnCreate.class, OnUpdate.class})
  @Length(max = 255, message = "Text cannot be longer than 255 characters.", groups = {
      OnCreate.class, OnUpdate.class})
  private String text;

  @Schema(description = "Comment data", example = "01-01-2024 00:00")
  @DateTimeFormat(iso = ISO.TIME)
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  private LocalDateTime dateCreated;

}

