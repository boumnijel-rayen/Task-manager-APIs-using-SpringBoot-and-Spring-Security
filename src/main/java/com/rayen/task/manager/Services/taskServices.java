package com.rayen.task.manager.Services;

import com.rayen.task.manager.Model.tasks;
import com.rayen.task.manager.Repo.tasksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class taskServices {

    @Autowired
    private tasksRepo tasksRepo;

    public tasks addTask(tasks tasks){
        return tasksRepo.save(tasks);
    }
}
