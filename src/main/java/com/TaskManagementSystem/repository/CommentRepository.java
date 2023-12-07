package com.TaskManagementSystem.repository;

import com.TaskManagementSystem.domain.comment.Comment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
  Page<Comment> findCommentsByTaskId(Long taskId, Pageable pageable);
}
