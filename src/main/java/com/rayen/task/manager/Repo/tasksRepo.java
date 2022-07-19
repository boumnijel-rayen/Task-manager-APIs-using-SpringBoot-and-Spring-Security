package com.rayen.task.manager.Repo;

import com.rayen.task.manager.Model.tasks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface tasksRepo extends JpaRepository<tasks,Long> {
}
