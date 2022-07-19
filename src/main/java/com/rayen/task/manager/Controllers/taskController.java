package com.rayen.task.manager.Controllers;

import com.rayen.task.manager.Exceptions.forbiddenException;
import com.rayen.task.manager.Model.tasks;
import com.rayen.task.manager.Services.taskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

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
    public tasks addTask(@RequestBody tasks tasks){
        return taskServices.addTask(tasks);
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        String imageName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String type = multipartFile.getContentType().substring(multipartFile.getContentType().indexOf("/")+1);
        if ((!type.equals("jpeg")) && (!type.equals("png"))){
            throw new forbiddenException("type of file must be jpeg or png !");
        }
        Path fileStorage = get(DIRECTORY,"13."+type).toAbsolutePath().normalize();
        copy(multipartFile.getInputStream(),fileStorage,REPLACE_EXISTING);
        return imageName;
    }
}
