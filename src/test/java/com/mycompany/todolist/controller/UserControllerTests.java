/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.controller;

import com.mycompany.todolist.model.Role;
import com.mycompany.todolist.model.User;
import com.mycompany.todolist.service.RoleService;
import com.mycompany.todolist.service.UserService;
import java.util.Arrays;
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
public class UserControllerTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private WebApplicationContext context;
    
    @BeforeEach
    public void setup(){
         mockMvc=MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
     }    
    
    @WithMockUser("spring")
    @Test
    public void getAllTest() throws Exception{
        List<User> expected=userService.getAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));                
        List<User> actual=(List<User>)mockMvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andReturn().getModelAndView().getModel().get("users");
        for(int i=0;i<expected.size();i++){
            assertEquals(expected.get(i).getEmail(),actual.get(i).getEmail());
        }
    }
    
    @WithMockUser("spring")
    @Test
    public void createGetTest() throws Exception{
        User expected=new User();
        mockMvc.perform(MockMvcRequestBuilders.get("/users/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
        User actual=(User)mockMvc.perform(MockMvcRequestBuilders.get("/users/create"))
                .andReturn().getModelAndView().getModel().get("user");
        assertEquals(expected.toString(),actual.toString());
    }
    
    @WithMockUser("spring")
    @Test
    public void createPostTest() throws Exception{        
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create").param("firstName","Bob")
                .param("lastName", "Robertson").param("email","bobrobertson@mail.com")
                .param("password","Aa12345678"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());               
    }
    
    @WithMockUser("spring")
    @Test
    public void createPostWithInvalidDataTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create").param("firstName","bob")
                .param("lastName", "Robertson").param("email","bobrobertson@mail.com")
                .param("password","Aa12345678"))
                .andExpect(MockMvcResultMatchers.status().isOk());                
    }
    
    @WithMockUser("spring")
    @Test
    public void readTest() throws Exception {
        int id=5;
        User expected=userService.readById(id);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/"+id+"/read"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
        User actual=(User)mockMvc.perform(MockMvcRequestBuilders.get("/users/"+id+"/read"))
                .andReturn().getModelAndView().getModel().get("user");
        assertEquals(expected.toString(),actual.toString());
    }
    
    @WithMockUser("spring")
    @Test
    public void readTestWithInValidId() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/users/20/read"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.model().attributeExists("infos"))
                //.andExpect(MockMvcResultMatchers.model().attributeExists("code"))
                .andExpect(MockMvcResultMatchers.model().attribute("infos",Arrays.asList("User with id 20 not found")));
    }
    
    @WithMockUser("spring")
    @Test
    public void updateGetTest() throws Exception{
        int id=5;
        User expectedUser=userService.readById(id);
        List<Role> expectedRoles=roleService.getAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/users/"+id+"/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("roles"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
        User actualUser=(User)mockMvc.perform(MockMvcRequestBuilders.get("/users/"+id+"/update"))
                .andReturn().getModelAndView().getModel().get("user");
        List<Role> actualRoles=(List<Role>)mockMvc.perform(MockMvcRequestBuilders.get("/users/"+id+"/update"))
                .andReturn().getModelAndView().getModel().get("roles");
        for(int i=0;i<expectedRoles.size();i++){
          assertEquals(expectedRoles.get(i).toString(),actualRoles.get(i).toString());
        }
        assertEquals(expectedUser.toString(),actualUser.toString());
    }
    
    @WithMockUser("spring")
    @Test
    public void updatePostTest() throws Exception{
        User user=userService.readById(5);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/5/update?roleId=1").param("firstName","Bob")
                .param("lastName", "Robertson").param("email",user.getEmail())
                .param("password",user.getPassword()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()); 
    }
    
    @WithMockUser("spring")
    @Test
    public void updatePostWithInvalidDataTest() throws Exception{        
        User user=userService.readById(5);
        List<Role> expectedRoles=roleService.getAll();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/5/update?roleId=1").param("firstName","bob")
                .param("lastName", "Robertson").param("email",user.getEmail())
                .param("password",user.getPassword()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());               
    }
    
    @WithMockUser("spring")
    @Test
    public void deleteTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/users/5/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
    
    @WithMockUser("spring")
    @Test
    public void deleteByInvalidIdTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/users/20/delete"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.model().attributeExists("infos"))
                //.andExpect(MockMvcResultMatchers.model().attributeExists("code"))
                .andExpect(MockMvcResultMatchers.model().attribute("infos",Arrays.asList("User with id 20 not found")));
    }    
    
}
