/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author dmytr
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled=true,
        securedEnabled=true,
        jsr250Enabled=true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter  {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{       
        http.authorizeRequests().antMatchers("/users/create").permitAll().anyRequest().authenticated().and().formLogin()
                .loginPage("/login-form").loginProcessingUrl("/login")
                .defaultSuccessUrl("/home").failureUrl("/login-form?error=true")
                .permitAll().and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login-form").deleteCookies("JSESSIONID");       
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, AuthenticationProvider provider) throws Exception{        
        auth.authenticationProvider(provider);
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
