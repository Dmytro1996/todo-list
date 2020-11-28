/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.service;

import com.mycompany.todolist.exceptions.NullEntityReferenceException;
import com.mycompany.todolist.model.Priority;
import com.mycompany.todolist.model.State;
import com.mycompany.todolist.model.Task;
import com.mycompany.todolist.model.ToDo;
import com.mycompany.todolist.repository.TaskRepository;
import com.mycompany.todolist.service.impl.TaskServiceImpl;
import java.util.Arrays;
import javax.persistence.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author dmytr
 */
@ExtendWith(SpringExtension.class)
public class TaskServiceTests {
    @TestConfiguration
    static class TaskServiceTestConfiguration{
        @Bean
        public TaskService taskService(){
            return new TaskServiceImpl();
        }
    }
    
    @Autowired
    private TaskService taskService;
    
    @MockBean
    private TaskRepository taskRepository;
    
    private Task task;
    
    @BeforeEach
    public void setUp(){
        task=new Task();
        task.setId(8L);
        task.setName("testTask");
        task.setPriority(Priority.HIGH);
        ToDo todo=new ToDo();
        todo.setId(14);
        todo.setTitle("todo#1");
        task.setTodo(todo);
        State state=new State();
        state.setName("In progress");
        task.setState(state);
        Mockito.when(taskRepository.findById(8L)).thenReturn(task);
        Mockito.when(taskRepository.save(task)).thenReturn(task);
        Mockito.when(taskRepository.findAll()).thenReturn(Arrays.asList(task));
        Mockito.when(taskRepository.findByToDoId(14)).thenReturn(Arrays.asList(task));
        Mockito.when(taskRepository.save(null)).thenThrow(new IllegalArgumentException());
    }
    
    @Test
    public void readByIdTestWithMock(){
        assertEquals(8,taskService.readById(8L).getId());
        Throwable e=assertThrows(EntityNotFoundException.class,()->taskService.readById(9));
        assertEquals("Task with id 9 not found",e.getMessage());
    }
    
    @Test
    public void createTestWithMock(){
        assertEquals(task.toString(),taskService.create(task).toString());
        NullEntityReferenceException e=assertThrows(NullEntityReferenceException.class,()->taskService.create(null));
        assertEquals(Arrays.asList("Task cannot be 'null'"),e.getMistakes());
    }
    
    @Test
    public void updateTestWithMock(){
        assertEquals(task.toString(),taskService.update(task).toString());
        NullEntityReferenceException e=assertThrows(NullEntityReferenceException.class,()->taskService.update(null));
        assertEquals(Arrays.asList("Task cannot be 'null'"),e.getMistakes());
    }
    
    @Test
    public void deleteTestWithMock(){
        Throwable e=assertThrows(EntityNotFoundException.class,()->taskService.delete(9L));
        assertEquals("Task with id 9 not found",e.getMessage());
    }
    
    @Test
    public void getAllTestWithMock(){        
        assertEquals(Arrays.asList(task).toString(),taskService.getAll().toString());
    }
    
    @Test
    public void getByTodoIdTestWithMock(){        
        assertEquals(Arrays.asList(task).toString(),taskService.getByTodoId(14).toString());
    }
}
