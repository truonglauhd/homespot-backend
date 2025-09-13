package com.homespot.repository;

import com.homespot.model.User;
import com.homespot.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByUser(User user);
}
