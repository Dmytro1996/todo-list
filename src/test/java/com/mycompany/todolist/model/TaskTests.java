/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.model;

import java.util.Set;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author dmytr
 */
@SpringBootTest
public class TaskTests {
    @Test
    void createValidTask() {
        Task validTask=new Task();
        validTask.setName("someTask");
        validTask.setPriority(Priority.LOW);
        validTask.setState(new State());
        validTask.setTodo(new ToDo());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(validTask);
        assertEquals(0, violations.size());
    }
    
    @Test
    void createValidTaskName() {
        Task validTask=new Task();
        validTask.setName("someTask");
        validTask.setPriority(Priority.LOW);
        validTask.setState(new State());
        validTask.setTodo(new ToDo());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(validTask);
        assertEquals(0, violations.size());
        assertEquals("someTask", validTask.getName());
    }
    
    @Test
    void createValidTaskPriority() {
        Task validTask=new Task();
        validTask.setName("someTask");
        validTask.setPriority(Priority.LOW);
        validTask.setState(new State());
        validTask.setTodo(new ToDo());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(validTask);
        assertEquals(0, violations.size());
        assertEquals(Priority.LOW, validTask.getPriority());
    }
    
    @Test
    void createValidTaskState() {
        Task validTask=new Task();
        validTask.setName("someTask");
        validTask.setPriority(Priority.LOW);
        State state=new State();
        state.setName("state");
        validTask.setState(state);
        validTask.setTodo(new ToDo());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(validTask);
        assertEquals(0, violations.size());
        assertEquals(state, validTask.getState());
    }
    
    @Test
    void createValidTaskToDo() {
        Task validTask=new Task();
        validTask.setName("someTask");
        validTask.setPriority(Priority.LOW);
        State state=new State();
        state.setName("state");
        ToDo todo=new ToDo();
        todo.setTitle("todo1");
        todo.setOwner(new User());
        validTask.setState(state);
        validTask.setTodo(todo);        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(validTask);
        assertEquals(0, violations.size());
        assertEquals(todo, validTask.getTodo());
    }
    
    @Test
    void toStringTest() {
        Task validTask=new Task();
        validTask.setName("someTask");
        validTask.setPriority(Priority.LOW);
        State state=new State();     
        state.setName("state");
        ToDo todo=new ToDo();
        todo.setTitle("todo1");
        todo.setOwner(new User());
        validTask.setState(state);
        validTask.setTodo(todo);        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(validTask);
        assertEquals(0, violations.size());
        String expected="Task {" +
                "id = " + validTask.getId() +
                ", name = '" + validTask.getName() + '\'' +
                ", priority = " + validTask.getPriority().toString() +
                ", todo = " + todo.toString() +
                ", state = " + state.toString() +
                "} ";
        assertEquals(expected, validTask.toString());
    }
    
    @ParameterizedTest
    @MethodSource("provideInvalidNames")
    void constraintViolationInvalidName(String input, String errorValue) {
        Task task=new Task();
        task.setName(input);
        task.setPriority(Priority.HIGH);
        task.setState(new State());
        task.setTodo(new ToDo());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }
    
    private static Stream<Arguments> provideInvalidNames(){
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of(null, null)                
        );
    }
    
    @ParameterizedTest
    @MethodSource("provideInvalidPriority")
    void constraintViolationInvalidPriority(Priority input, Priority errorValue) {
        Task task=new Task();
        task.setName("task");
        task.setPriority(input);
        task.setState(new State());
        task.setTodo(new ToDo());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }
    
    private static Stream<Arguments> provideInvalidPriority(){
        return Stream.of(Arguments.of(null, null));
    }       
    
}
