/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.controller;

import com.mycompany.todolist.model.Priority;
import com.mycompany.todolist.model.State;
import com.mycompany.todolist.model.Task;
import com.mycompany.todolist.model.ToDo;
import com.mycompany.todolist.service.StateService;
import com.mycompany.todolist.service.TaskService;
import com.mycompany.todolist.service.ToDoService;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
public class TaskControllerTests {
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private ToDoService todoService;
    
    @Autowired
    private StateService stateService;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext context;
    
    @BeforeEach
    public void setup(){
         mockMvc=MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
     }
    
    @WithMockUser("spring")
    @Test
    public void createGetTest() throws Exception{        
        ToDo expectedTodo=todoService.readById(7);
        Priority[] expectedPriorities=Priority.values();
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/create/todos/7"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("task"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("priorities"));
        Task task=(Task)mockMvc.perform(MockMvcRequestBuilders
                .get("/tasks/create/todos/7")).andReturn().getModelAndView()
                .getModel().get("task");
        ToDo actualTodo=(ToDo)mockMvc
                .perform(MockMvcRequestBuilders.get("/tasks/create/todos/7"))
                .andReturn().getModelAndView().getModel().get("todo");
        Priority[] actualPriorities=(Priority[])mockMvc
                .perform(MockMvcRequestBuilders.get("/tasks/create/todos/7"))
                .andReturn().getModelAndView().getModel().get("priorities");
        assertEquals(task.getId(),0);
        assertEquals(task.getName(),null);
        assertEquals(expectedTodo.toString(),actualTodo.toString());
        assertEquals(Arrays.toString(expectedPriorities),Arrays.toString(actualPriorities)); 
    }
    
    @WithMockUser("spring")
    @Test
    public void createWithInvalidDataTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/create/todos/20"))                
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())                
                .andExpect(MockMvcResultMatchers.model().attributeExists("infos"))
                .andExpect(MockMvcResultMatchers.model().attribute("infos",Arrays.asList("To-Do with id 20 not found")));                        
    }
    
    @WithMockUser("spring")
    @Test
    public void createPostTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/create/todos/8")
                .param("name","SomeTask").param("priority",Priority.MEDIUM.toString())
                .with(csrf()))                
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
    
    @WithMockUser("spring")
    @Test
    public void updateGetTest() throws Exception{
        List<State> expectedStates=stateService.getAll();
        Priority[] expectedPriorities=Priority.values();
        Task expectedTask=taskService.readById(7);
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/"+expectedTask.getId()+"/update/todos/7"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("task"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("states"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("priorities"));
         Task actualTask=(Task)mockMvc.perform(MockMvcRequestBuilders
                .get("/tasks/"+expectedTask.getId()+"/update/todos/7")).andReturn().getModelAndView()
                .getModel().get("task");
        List<State> actualStates=(List<State>)mockMvc
                .perform(MockMvcRequestBuilders.get("/tasks/"+expectedTask.getId()+"/update/todos/7"))
                .andReturn().getModelAndView().getModel().get("states");
        Priority[] actualPriorities=(Priority[])mockMvc
                .perform(MockMvcRequestBuilders.get("/tasks/"+expectedTask.getId()+"/update/todos/7"))
                .andReturn().getModelAndView().getModel().get("priorities");
        assertEquals(expectedTask.getId(),actualTask.getId());
        assertEquals(expectedStates.toString(),actualStates.toString());
        assertEquals(Arrays.toString(expectedPriorities),Arrays.toString(actualPriorities));        
    }
    
    @WithMockUser("spring")
    @Test
    public void updatePostTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/7/update/todos/7")
                .param("name","SomeTask").param("priority",Priority.MEDIUM.toString()).param("id", "7").with(csrf()))                
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
    
    @WithMockUser("spring")
    @Test
    public void updateWithInValidDataTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/20/update/todos/7"))                
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.model().attributeExists("infos"))
                .andExpect(MockMvcResultMatchers.model().attribute("infos",Arrays.asList("Task with id 20 not found")));                
    }
    
    @WithMockUser("spring")
    @Test
    public void deleteTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/7/delete/todos/7"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
    
    @WithMockUser("spring")
    @Test
    public void deleteByInValidIdTest() throws Exception{
         mockMvc.perform(MockMvcRequestBuilders.get("/tasks/20/delete/todos/7"))                
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.model().attributeExists("infos"))
                .andExpect(MockMvcResultMatchers.model().attribute("infos",
                        Arrays.asList("Task with id 20 not found")));                
    }
    
}
