package com.rayen.task.manager.Repo;

import com.rayen.task.manager.Model.role;
import com.rayen.task.manager.Model.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface userRepo extends JpaRepository<user,Long> {
    user findByUsername(String username);
    List<user> findByRolesNotContains(role role);
}
