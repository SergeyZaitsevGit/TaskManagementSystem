package com.TaskManagementSystem.web.dto.task;

import com.TaskManagementSystem.domain.task.Priority;
import com.TaskManagementSystem.domain.task.Status;
import com.TaskManagementSystem.web.validation.OnCreate;
import com.TaskManagementSystem.web.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TaskDto {

  @Schema(description = "Task Id", example = "1")
  @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
  @Null(message = "Id must be null.", groups = OnCreate.class)
  private Long id;

  @Schema(description = "Task title", example = "Go to the supermarket")
  @NotNull(message = "Title must be not null.", groups = {OnCreate.class, OnUpdate.class})
  @Length(max = 255, message = "Title cannot be longer than 255 characters.", groups = {
      OnCreate.class, OnUpdate.class})
      private String title;

  @Schema(description = "Task title", example = "Buy eggs, milk, bread")
  @Length(max = 255, message = "Description cannot be longer than 255 characters.", groups = {
      OnCreate.class, OnUpdate.class})
  private String description;

  @Schema(description = "Task status", example = "IN_PROGRESS")
  private Status status;

  @Schema(description = "Task priority", example = "MEDIUM")
  private Priority priority;

}
