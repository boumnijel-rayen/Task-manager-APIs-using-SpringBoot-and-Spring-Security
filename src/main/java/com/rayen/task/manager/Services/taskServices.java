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
import com.rayen.task.manager.Services.formats.forChartDashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
        if (tasks.getTitle().length() <= 0){
            throw new forbiddenException("title is required");
        }
        if (tasks.getDescription().length() < 10){
            throw new forbiddenException("description must be more than 10 character");
        }
        if (tasks.getEnd().before(tasks.getStart())){
            throw new forbiddenException("error on dates");
        }
        return tasksRepo.save(tasks);
    }

    public String deleteTask(long id){
        try{
            tasks tasks = tasksRepo.findById(id).get();
            tasks.setUser(null);
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

    public tasks assignDone(long id,String request) throws ParseException {
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
        Date dateNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(formatter.format(dateNow), DateTimeFormatter.ISO_DATE).plusDays(1);
        Date doneTime = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        tasks.setDoneTime(doneTime);
        return tasksRepo.save(tasks);
    }

    public int tasksForToday(){
        return tasksRepo.findByDoneEquals(false).size();
    }

    public int tasksDone(){ return tasksRepo.findByDoneEquals(true).size(); }

    public int tasksLate(){
        List<tasks> tasks = new ArrayList<>();
        Date currentDate = new Date();
        tasks = tasksRepo.findByDoneEquals(false);
        List<tasks> finalTasks = new ArrayList<>();
        tasks.forEach((task) -> {
            if (task.getEnd().before(currentDate)){
                finalTasks.add(task);
            }
        });
        return finalTasks.size();
    }

    public List<forChartDashboard> chartDashboard() {

        Date date1 = new Date();
        List<forChartDashboard> result = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        for(int i=0;i<10;i++){
            LocalDate date = LocalDate.parse(formatter.format(date1), DateTimeFormatter.ISO_DATE).minusDays(i-1);
            Date doneTime = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            forChartDashboard forChartDashboard = new forChartDashboard();
            forChartDashboard.setDate(doneTime);
            forChartDashboard.setNumberOfTasks(tasksRepo.findByDoneTime(doneTime).size());
            result.add(forChartDashboard);
        }
        return result;
    }

    public int getNumberOfTasksPerUser(long id,String request){
        String token = request.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String usernameToken = decodedJWT.getSubject();
        String username = userRepo.findById(id).get().getUsername();
        List<String> roles = new ArrayList<>();
        roles = decodedJWT.getClaim("roles").asList(String.class);
        if ((!usernameToken.equals(username)) && (!roles.contains("RO" +
                "LE_ADMIN")) ){
            throw new forbiddenException("you can't get another user !");
        }
        user user = userRepo.findById(id).get();

        return tasksRepo.findByUser(user).size();
    }

    public List<tasks> getDonePerUser(long id, String request) {
        String token = request.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String usernameToken = decodedJWT.getSubject();
        String username = userRepo.findById(id).get().getUsername();
        List<String> roles = new ArrayList<>();
        roles = decodedJWT.getClaim("roles").asList(String.class);
        if ((!usernameToken.equals(username)) && (!roles.contains("RO" +
                "LE_ADMIN")) ){
            throw new forbiddenException("you can't get another user !");
        }

        List<tasks> tasks =  new ArrayList<>();
        List<tasks> result = new ArrayList<>();
        tasks = tasksRepo.findByUser(userRepo.findById(id).get());
        tasks.forEach(task -> {
            if (task.getDone() == true){
                result.add(task);
            }
        });

        return result;
    }

    public List<tasks> getRetardPerUser(long id, String request) {
        String token = request.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String usernameToken = decodedJWT.getSubject();
        String username = userRepo.findById(id).get().getUsername();
        List<String> roles = new ArrayList<>();
        roles = decodedJWT.getClaim("roles").asList(String.class);
        if ((!usernameToken.equals(username)) && (!roles.contains("RO" +
                "LE_ADMIN")) ){
            throw new forbiddenException("you can't get another user !");
        }

        List<tasks> tasks =  new ArrayList<>();
        List<tasks> result = new ArrayList<>();
        Date currentDate = new Date();
        tasks = tasksRepo.findByUser(userRepo.findById(id).get());
        tasks.forEach(task -> {
            if ( (task.getDone() == false) && (task.getEnd().before(currentDate)) ){
                result.add(task);
            }
        });

        return result;
    }

    public List<tasks> getCoursPerUser(long id, String request) {
        String token = request.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String usernameToken = decodedJWT.getSubject();
        String username = userRepo.findById(id).get().getUsername();
        List<String> roles = new ArrayList<>();
        roles = decodedJWT.getClaim("roles").asList(String.class);
        if ((!usernameToken.equals(username)) && (!roles.contains("RO" +
                "LE_ADMIN")) ){
            throw new forbiddenException("you can't get another user !");
        }

        List<tasks> tasks =  new ArrayList<>();
        List<tasks> result = new ArrayList<>();
        Date currentDate = new Date();
        tasks = tasksRepo.findByUser(userRepo.findById(id).get());
        tasks.forEach(task -> {
            if ( (task.getDone() == false) && (task.getEnd().after(currentDate)) ){
                result.add(task);
            }
        });

        return result;
    }

}
