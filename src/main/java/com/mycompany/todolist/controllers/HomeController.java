package com.mycompany.todolist.controllers;

import com.mycompany.todolist.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final UserService userService;
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "home"})
    public String home(Model model) {
        model.addAttribute("users", userService.getAll());
        return "home";
    }
    
    @GetMapping("/access-denied")
    public String accessDenied(Model model){
        return "access-denied";
    }
    
}
