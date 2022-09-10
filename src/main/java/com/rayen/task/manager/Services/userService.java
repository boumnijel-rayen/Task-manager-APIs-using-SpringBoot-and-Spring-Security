package com.rayen.task.manager.Services;

import com.rayen.task.manager.Model.role;
import com.rayen.task.manager.Model.user;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface userService {
    user saveUser(user user, MultipartFile multipartFile) throws IOException;
    role saveRole(role role);
    void addRoleToUser(String username,String name);
    user getUser(long id, String request) throws IOException;
    ResponseEntity<Resource> getImageUser(long id,String Authorization) throws IOException;
    String deleteUser(long id);
    List<user> getUsers();
    List<role> getRoles();
    long getId(String username,String request) throws IOException;
    Boolean isExiste(String username);
}
