package com.rayen.task.manager.Repo;

import com.rayen.task.manager.Model.tasks;
import com.rayen.task.manager.Model.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface tasksRepo extends JpaRepository<tasks,Long> {
    List<tasks> findByUser(user user);
    List<tasks> findByDoneEquals(boolean done);
    List<tasks> findByDoneTime(Date date);

}
