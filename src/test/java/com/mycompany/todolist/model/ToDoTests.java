/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
/**
 *
 * @author dmytr
 */
public class ToDoTests {
    @Test
    void createValidToDoOwner() {
        ToDo todo=new ToDo();
        todo.setTitle("someTask");
        User user=new User();
        user.setFirstName("Name");
        user.setLastName("Name");
        todo.setOwner(user);        
        //todo.s(new State());        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(todo);
        assertEquals(0, violations.size());
        assertEquals(user.getId(), todo.getOwner().getId());
        assertEquals(user.getFirstName(), todo.getOwner().getFirstName());
        assertEquals(user.getLastName(), todo.getOwner().getLastName());
    }
    
    @Test
    void createValidToDoTasks() {
        ToDo todo=new ToDo();
        todo.setTitle("someTask");
        User user=new User();
        user.setFirstName("Name");
        user.setLastName("Name");
        todo.setOwner(user);
        List<Task> tasks=new ArrayList<>(Arrays.asList(new Task(),new Task()));
        todo.setTasks(tasks);        
        //todo.s(new State());        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(todo);
        assertEquals(0, violations.size());
        assertIterableEquals(tasks,todo.getTasks());        
    }
}
