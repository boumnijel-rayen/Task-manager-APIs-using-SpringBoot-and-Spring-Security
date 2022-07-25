package com.rayen.task.manager.Security;

import com.rayen.task.manager.Filter.CustomAuthenticationFilter;
import com.rayen.task.manager.Filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] ALL_ROLES ={"ROLE_USER","ROLE_ADMIN"};
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().antMatchers("/login").permitAll();
        http.authorizeHttpRequests().antMatchers(POST,"/user").permitAll();
        http.authorizeHttpRequests().antMatchers(GET,"/user").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeHttpRequests().antMatchers(GET,"/user/role").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeHttpRequests().antMatchers(POST,"/user/role").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeHttpRequests().antMatchers(POST,"/user/role/addtouser").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeHttpRequests().antMatchers(GET,"/user/**").hasAnyAuthority(ALL_ROLES);
        http.authorizeHttpRequests().antMatchers(DELETE,"/user/**").hasAnyAuthority(ALL_ROLES);
        http.authorizeHttpRequests().antMatchers(POST,"/task").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeHttpRequests().antMatchers(DELETE,"/task/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeHttpRequests().antMatchers(GET,"/task").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeHttpRequests().antMatchers(GET,"/task/{id}").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeHttpRequests().antMatchers(GET,"/task/user/{id}").hasAnyAuthority(ALL_ROLES);
        http.authorizeHttpRequests().antMatchers(POST,"/task/{idT}/user/{idU}").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeHttpRequests().antMatchers(PUT,"/task/{id}").hasAnyAuthority(ALL_ROLES);
        http.authorizeHttpRequests().anyRequest().permitAll();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
