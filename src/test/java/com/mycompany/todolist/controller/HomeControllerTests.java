/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.controller;

import com.mycompany.todolist.model.User;
import com.mycompany.todolist.service.UserService;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author dmytr
 */
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class HomeControllerTests {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private WebApplicationContext context;
    
     @Autowired
    private MockMvc mockMvc;
     
     /*@BeforeEach
     public void setup(){
         mockMvc=MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
     }*/
    
    @WithMockUser("spring")
    @Test
    public void homeTest() throws Exception{
        List<User> expectedUsers=userService.getAll();
         mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
         mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
        List<User> actualUsers=(List<User>)mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andReturn().getModelAndView().getModel().get("users");
        assertEquals(expectedUsers.toString(),actualUsers.toString());
    }
}
