package com.rayen.task.manager.Repo;

import com.rayen.task.manager.Model.role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roleRepo extends JpaRepository<role,Long> {
    role findByName(String name);
}
