package com.mycompany.todolist.controller;

import com.mycompany.todolist.model.ToDo;
import com.mycompany.todolist.repository.TaskRepository;
import com.mycompany.todolist.repository.UserRepository;
import com.mycompany.todolist.service.ToDoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ToDoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ToDoService toDoService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private WebApplicationContext context;
    
    @BeforeEach
    public void setup(){
         mockMvc=MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
     }
    
    @WithMockUser("spring")
    @Test
    public void shouldAddToDo() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/todos/create/users/{owner_id}", "4")
                .param("createdAt", "10-10-2017")
                .param("title", "Title")
                .param("ownerId", "4")
        )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }
    
    @WithMockUser("spring")
    @Test
    public void shouldReturnTasksByToDoId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/{id}/tasks", "8"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }
    
    @WithMockUser("spring")
    @Test
    public void shouldUpdateToDo() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/{todo_id}/update/users/{owner_id}", "7", "4"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }
    
    @WithMockUser("spring")
    @Test
    public void shouldDeleteToDo() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/{todo_id}/delete/users/{owner_id}", "7", "4"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

    }
    
    @WithMockUser("spring")
    @Test
    public void shouldGetAllToDos() throws Exception {
        List<ToDo> expected = toDoService.getByUserId(4L);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/all/users/{user_id}", "4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todos"));                
        List<ToDo> actual=(List<ToDo>)mockMvc.perform(MockMvcRequestBuilders.get("/todos/all/users/{user_id}", "4"))
                .andReturn().getModelAndView().getModel().get("todos");
        assertEquals(expected.toString(),actual.toString());
    }
    
    @WithMockUser("spring")
    @Test
    public void shouldAddCollaborator() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/{id}/add", "7")
                .param("user_id", "4"))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

    }
    
    @WithMockUser("spring")
    @Test
    public void shouldRemoveCollaborator() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/{id}/remove", "7")
                .param("user_id", "4"))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

    }
}
