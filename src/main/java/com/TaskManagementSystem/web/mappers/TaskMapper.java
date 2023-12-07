package com.TaskManagementSystem.web.mappers;

import com.TaskManagementSystem.domain.task.Task;
import com.TaskManagementSystem.web.dto.task.TaskDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  TaskDto toDto(Task task);

  List<TaskDto> toDto(List<Task> taskDtoList);

  Task toEntity(TaskDto taskDto);

}
