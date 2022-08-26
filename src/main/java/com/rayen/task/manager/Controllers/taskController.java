package com.rayen.task.manager.Controllers;

import com.rayen.task.manager.Exceptions.forbiddenException;
import com.rayen.task.manager.Model.tasks;
import com.rayen.task.manager.Services.formats.forChartDashboard;
import com.rayen.task.manager.Services.taskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RestController
@RequestMapping("/task")
public class taskController {

    private static final String DIRECTORY = System.getProperty("user.home") + "/Desktop/task-manager/upload";

    @Autowired
    private taskServices taskServices;

    @PostMapping("")
    public ResponseEntity<tasks> addTask(@RequestBody tasks tasks){
        return ResponseEntity.ok().body(taskServices.addTask(tasks));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable long id){
        return ResponseEntity.ok().body(taskServices.deleteTask(id));
    }

    @GetMapping("")
    public ResponseEntity<List<tasks>> getAllTasks(){
        return ResponseEntity.ok().body(taskServices.getAll());
    }

    //ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<tasks> getTask(@PathVariable long id){
        return ResponseEntity.ok().body(taskServices.getOneTask(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<tasks>> getTasksByUser(@PathVariable long id,@RequestHeader String Authorization){
        return ResponseEntity.ok().body(taskServices.getTasksByUser(id,Authorization));
    }

    @PostMapping("/{idT}/user/{idU}")
    public void assignTaskToUser(@PathVariable("idU") long idU,@PathVariable("idT") long idT){
        taskServices.assignTaskToUser(idU,idT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<tasks> assignDone(@PathVariable long id,@RequestHeader String Authorization) throws ParseException {
        return ResponseEntity.ok().body(taskServices.assignDone(id,Authorization));
    }

    @GetMapping("/dashboard/tasksDone")
    public int tasksDone(){
        return taskServices.tasksDone();
    }

    @GetMapping("/dashboard/tasksLate")
    public int tasksLate(){
        return taskServices.tasksLate();
    }

    @GetMapping("/dashboard/tasksfrotoday")
    public int tasksForToday(){
        return taskServices.tasksForToday();
    }

    @GetMapping("/dashboard/chart")
    public List<forChartDashboard> chartDashboard(){
        return taskServices.chartDashboard();
    }

    @GetMapping("/test")
    public int test(){
        return taskServices.tasksDone();
    }
}
