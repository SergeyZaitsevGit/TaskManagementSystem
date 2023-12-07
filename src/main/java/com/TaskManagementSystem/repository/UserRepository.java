package com.TaskManagementSystem.repository;

import com.TaskManagementSystem.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);


}
