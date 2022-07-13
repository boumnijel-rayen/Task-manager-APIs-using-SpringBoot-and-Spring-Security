package com.rayen.task.manager.Services;

import com.rayen.task.manager.Model.role;
import com.rayen.task.manager.Model.user;
import com.rayen.task.manager.Repo.roleRepo;
import com.rayen.task.manager.Repo.userRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class userServiceImp implements userService, UserDetailsService {

    @Autowired
    private userRepo userRepo;
    @Autowired
    private roleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user user = userRepo.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("user not found");
        }
        Collection<SimpleGrantedAuthority> authorites = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorites.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(user.getUsername(),user.getPassword(),authorites);
    }

    @Override
    public user saveUser(user user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public role saveRole(role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String name) {
        user user = userRepo.findByUsername(username);
        role role = roleRepo.findByName(name);
        user.getRoles().add(role);
    }

    @Override
    public user getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<user> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<role> getRoles() {
        return roleRepo.findAll();
    }
}
