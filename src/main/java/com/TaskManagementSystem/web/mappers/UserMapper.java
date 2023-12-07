package com.TaskManagementSystem.web.mappers;

import com.TaskManagementSystem.domain.user.User;
import com.TaskManagementSystem.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(User user);
  User toEntity(UserDto userDto);
}
