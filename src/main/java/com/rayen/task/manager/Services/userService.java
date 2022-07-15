package com.rayen.task.manager.Services;

import com.rayen.task.manager.Model.role;
import com.rayen.task.manager.Model.user;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface userService {
    user saveUser(user user);
    role saveRole(role role);
    void addRoleToUser(String username,String name);
    user getUser(long id, String request);
    String deleteUser(long id,String request);
    List<user> getUsers();
    List<role> getRoles();
}
