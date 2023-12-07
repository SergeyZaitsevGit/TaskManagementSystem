package com.TaskManagementSystem.web.security;

import com.TaskManagementSystem.domain.user.Role;
import com.TaskManagementSystem.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtEntityFactory {

  public static JwtEntity create(User user) {
    return new JwtEntity(
        user.getId(),
        user.getEmail(),
        user.getPassword(),
        new ArrayList<>(mapToGrandAuthority(user.getRoles()))
    );
  }

  private static List<GrantedAuthority> mapToGrandAuthority(Set<Role> roleSet) {
    return roleSet
        .stream()
        .map(Enum::name)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
