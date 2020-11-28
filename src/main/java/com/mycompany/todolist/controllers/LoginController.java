/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.controllers;

import com.mycompany.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author dmytr
 */
@Controller
public class LoginController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login-form")
    public String login(Model model){                
        return "login-form";
    }

    @RequestMapping("/logout")
    public String performLogout(Model model){        
        SecurityContextHolder.clearContext();
        return "redirect:/login-form?logout=true";
    }     
    
}
