package com.rayen.task.manager.Controllers;

import com.rayen.task.manager.Services.Functions.checkUserFunctions;
import com.rayen.task.manager.Services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @Autowired
    private userService userService;

    @GetMapping(value = {"","/"})
    public String sayHello(){
        return "hello hello !";
    }

    @GetMapping("/test")
    public Boolean test(@RequestBody String chaine){
        return checkUserFunctions.isEmail(chaine);
    }

    @GetMapping("/existe/{username}")
    public ResponseEntity<Boolean> isExiste(@PathVariable String username){
        return ResponseEntity.ok().body(userService.isExiste(username));
    }
}
