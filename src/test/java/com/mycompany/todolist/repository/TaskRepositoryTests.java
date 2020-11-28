/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.repository;

import com.mycompany.todolist.model.Priority;
import com.mycompany.todolist.model.State;
import com.mycompany.todolist.model.Task;
import com.mycompany.todolist.model.ToDo;
import com.mycompany.todolist.model.User;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
/**
 *
 * @author dmytr
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTests {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    public void getByTodoIdTest(){
        ToDo todo=new ToDo();
        todo.setTitle("someTodo");
        User user=entityManager.find(User.class, 5L);
        todo.setOwner(user);
        todo.setCreatedAt(LocalDateTime.now());
        Task task=new Task();
        task.setName("someTask");        
        task.setPriority(Priority.MEDIUM);
        task.setState(entityManager.find(State.class, 5L));
        task.setTodo(todo);
        entityManager.persist(todo);
        entityManager.persist(task);
        System.out.println(todo.getId());
        Task actualTask=taskRepository.findByToDoId(todo.getId()).get(0);
        assertEquals(task.toString(),actualTask.toString());
    }
}
