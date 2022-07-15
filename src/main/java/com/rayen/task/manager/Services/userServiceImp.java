package com.rayen.task.manager.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rayen.task.manager.Exceptions.forbiddenException;
import com.rayen.task.manager.Model.role;
import com.rayen.task.manager.Model.user;
import com.rayen.task.manager.Repo.roleRepo;
import com.rayen.task.manager.Repo.userRepo;
import com.rayen.task.manager.Services.Functions.checkUserFunctions;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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
    public user saveUser(user user) throws forbiddenException{
        if ((user.getPassword().length()<8)|| (checkUserFunctions.haveLettre(user.getPassword()) == false) || (checkUserFunctions.haveNum(user.getPassword())) == false){
            throw new forbiddenException("password invalid !");
        }
        if (userRepo.findByUsername(user.getUsername()) != null){
            throw new forbiddenException("username existed !");
        }
        if (!checkUserFunctions.isEmail(user.getEmail())){
            throw new forbiddenException("email invalid !");
        }
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
    public user getUser(long id, String request) {
        String token = request.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String usernameToken = decodedJWT.getSubject();
        String username = userRepo.findById(id).get().getUsername();
        if (!usernameToken.equals(username)){
            throw new forbiddenException("you can't get another user !");
        }
        return userRepo.findById(id).get();
    }

    @Override
    public List<user> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<role> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public String deleteUser(long id, String request) {
        String token = request.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String usernameToken = decodedJWT.getSubject();
        String username = userRepo.findById(id).get().getUsername();
        if (!usernameToken.equals(username)){
            throw new forbiddenException("you can't delete another user !");
        }
        userRepo.delete(userRepo.findById(id).get());
        return "profile has deleted !";
    }
}
