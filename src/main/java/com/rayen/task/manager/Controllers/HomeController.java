package com.rayen.task.manager.Controllers;

import com.rayen.task.manager.Services.Functions.checkUserFunctions;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = {"","/"})
    public String sayHello(){
        return "hello hello !";
    }

    @GetMapping("/test")
    public Boolean test(@RequestBody String chaine){
        return checkUserFunctions.isEmail(chaine);
    }
}
