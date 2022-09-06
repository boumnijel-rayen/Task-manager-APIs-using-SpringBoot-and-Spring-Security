package com.rayen.task.manager.Controllers;

import com.rayen.task.manager.Model.role;
import com.rayen.task.manager.Model.user;
import com.rayen.task.manager.Services.userService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private userService userService;

    @GetMapping("")
    public ResponseEntity<List<user>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/role")
    public ResponseEntity<List<role>> getRoles(){
        return ResponseEntity.ok().body(userService.getRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<user> getUser(@PathVariable long id, @RequestHeader String Authorization) throws IOException {
        return ResponseEntity.ok().body(userService.getUser(id,Authorization));
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getImageUser(@PathVariable long id, @RequestHeader String Authorization) throws IOException{
        return userService.getImageUser(id,Authorization);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<Long> getId(@PathVariable String username,@RequestHeader String Authorization) throws IOException{
        return ResponseEntity.ok().body(userService.getId(username,Authorization));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id){
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

    @PostMapping("")
    public ResponseEntity<user> saveUser(@ModelAttribute user user, @RequestParam("image")MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok().body(userService.saveUser(user,multipartFile));
    }

    @PostMapping("/role")
    public ResponseEntity<role> saveRole(@RequestBody role role){
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody roleToUser roleToUser){
        userService.addRoleToUser(roleToUser.getUsername(),roleToUser.getName());
        return ResponseEntity.ok().build();
    }
}

@Data
class roleToUser{
    private String username;
    private String name;
}
