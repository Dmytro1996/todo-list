/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist;

import com.mycompany.todolist.details.RoleAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author dmytr
 */
@Component
public class WebAuthenticationProvider implements AuthenticationProvider{
    
    @Autowired
    private UserDetailsService userDetailsService;    
    
    @Autowired
    private BCryptPasswordEncoder passEncoder;   
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{        ;
        String username=authentication.getName();        
        String password=authentication.getCredentials().toString();             
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        RoleAuthority role=(RoleAuthority)userDetails.getAuthorities().toArray()[0];        
        if(userDetails!=null && passEncoder.matches(password,userDetails.getPassword())){            
            return new UsernamePasswordAuthenticationToken(userDetails,
                    userDetails.getPassword(),userDetails.getAuthorities());
        }
        return null;
    }
    
    @Override
    public boolean supports(Class<?> authentication){
        return true;
    }
}
