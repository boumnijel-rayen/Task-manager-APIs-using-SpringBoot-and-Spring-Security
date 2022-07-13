package com.rayen.task.manager.Services;

import com.rayen.task.manager.Model.role;
import com.rayen.task.manager.Model.user;

import java.util.List;

public interface userService {
    user saveUser(user user);
    role saveRole(role role);
    void addRoleToUser(String username,String name);
    user getUser(String username);
    List<user> getUsers();
    List<role> getRoles();
}
