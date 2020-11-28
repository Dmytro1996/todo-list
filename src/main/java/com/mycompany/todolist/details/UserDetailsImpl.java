/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.details;

import com.mycompany.todolist.model.User;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author dmytr
 */
public class UserDetailsImpl implements UserDetails {
    
    private long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> roles;
    
    public UserDetailsImpl(User user){
        this.id=user.getId();
        this.password=user.getPassword();
        this.username=user.getEmail();
        this.roles=Arrays.asList(new RoleAuthority(user.getRole()));
    }

    public long getId() {
        return id;
    }
    
    @Override
    public String getUsername() {
        return username;
    }    
    
    public String getPassword() {
        return password;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }   
        
    @Override
    public boolean isEnabled(){
        return true;
    }
    
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
}
