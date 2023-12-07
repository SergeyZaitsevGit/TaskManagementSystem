package com.TaskManagementSystem.web.mappers;

import com.TaskManagementSystem.domain.comment.Comment;
import com.TaskManagementSystem.web.dto.comment.CommentDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

  CommentDto toDto(Comment comment);

  List<CommentDto> toDto(List<Comment> commentList);

  Comment toEntity(CommentDto commentDto);
}
