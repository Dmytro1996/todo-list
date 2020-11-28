/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.details;

import com.mycompany.todolist.model.Role;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author dmytr
 */
public class RoleAuthority implements GrantedAuthority {
    private long id;
    private String authority;
    
    public RoleAuthority(Role role){
        this.id=role.getId();
        this.authority=role.getName();
    }

    public long getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
