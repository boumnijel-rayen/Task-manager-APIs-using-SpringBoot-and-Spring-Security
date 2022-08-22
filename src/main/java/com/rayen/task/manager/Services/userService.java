package com.rayen.task.manager.Services;

import com.rayen.task.manager.Model.role;
import com.rayen.task.manager.Model.user;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface userService {
    user saveUser(user user, MultipartFile multipartFile) throws IOException;
    role saveRole(role role);
    void addRoleToUser(String username,String name);
    user getUser(long id, String request) throws IOException;
    String deleteUser(long id,String request);
    List<user> getUsers();
    List<role> getRoles();
    long getId(String username,String request) throws IOException;
}
