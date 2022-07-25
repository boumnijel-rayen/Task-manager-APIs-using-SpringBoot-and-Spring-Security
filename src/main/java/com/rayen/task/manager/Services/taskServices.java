package com.rayen.task.manager.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rayen.task.manager.Exceptions.NotFoundException;
import com.rayen.task.manager.Exceptions.forbiddenException;
import com.rayen.task.manager.Model.tasks;
import com.rayen.task.manager.Model.user;
import com.rayen.task.manager.Repo.tasksRepo;
import com.rayen.task.manager.Repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class taskServices {

    @Autowired
    private tasksRepo tasksRepo;
    @Autowired
    private userRepo userRepo;

    public tasks addTask(tasks tasks){
        tasks.setDone(false);
        return tasksRepo.save(tasks);
    }

    public String deleteTask(long id){
        try{
            tasks tasks = tasksRepo.findById(id).get();
            tasksRepo.delete(tasks);
        }catch (Exception e){
            throw new NotFoundException("task not found");
        }
        return "task has deleted !";
    }

    public List<tasks> getAll(){
        return tasksRepo.findAll();
    }

    public tasks getOneTask(long id){
        return tasksRepo.findById(id).get();
    }

    public List<tasks> getTasksByUser(long id,String request){
        String token = request.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String usernameToken = decodedJWT.getSubject();
        String username = userRepo.findById(id).get().getUsername();
        if (!usernameToken.equals(username)){
            throw new forbiddenException("you can't get tasks from another user !");
        }

        user user = userRepo.findById(id).get();
        return tasksRepo.findByUser(user);
    }

    public void assignTaskToUser(long idU,long idT){
        user user = userRepo.findById(idU).get();
        tasks tasks = tasksRepo.findById(idT).get();
        tasks.setUser(user);
    }

    public tasks assignDone(long id,String request){
        try{
            String token = request.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String usernameToken = decodedJWT.getSubject();
            String username = tasksRepo.findById(id).get().getUser().getUsername();
            if (!usernameToken.equals(username)){
                throw new forbiddenException("you can't assign done to tasks from another user !");
            }
        }catch (Exception e){
            throw new forbiddenException("you can't assign done to tasks from another user !");
        }



        tasks tasks = tasksRepo.findById(id).get();
        tasks.setDone(true);
        return tasksRepo.save(tasks);
    }

}
