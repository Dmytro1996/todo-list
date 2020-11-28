/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.repository;

import com.mycompany.todolist.model.Role;
import com.mycompany.todolist.model.ToDo;
import com.mycompany.todolist.model.User;
import java.time.LocalDateTime;
import java.util.Arrays;
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
public class ToDoRepositoryTests {
    
    @Autowired
    private ToDoRepository todoRepository;
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    public void getByUserIdTest(){
        User user=new User();
        user.setFirstName("Andrew");
        user.setLastName("Anderson");
        user.setEmail("andrewandersom@mail.com");
        user.setPassword("Aa12345678");
        Role role=new Role();
        role.setName("Developer");
        entityManager.persist(role);
        user.setRole(role);
        ToDo todo=new ToDo();
        todo.setTitle("someTodo");
        todo.setCreatedAt(LocalDateTime.now());        
        todo.setOwner(user);
        entityManager.persist(user);
        entityManager.persist(todo);
        assertEquals(Arrays.asList(todo).toString(),todoRepository.findByOwnerId(user.getId()).toString());
    }
}
