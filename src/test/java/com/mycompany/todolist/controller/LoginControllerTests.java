/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author dmytr
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class LoginControllerTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void loginTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/login-form"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void logoutTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/logout"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
