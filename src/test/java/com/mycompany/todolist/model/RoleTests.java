package com.mycompany.todolist.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@SpringBootTest
class RoleTests {
    
    @Test
    void checkRoleWithValidName() {
        Role role = new Role();
        role.setName("Name");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertEquals(0, violations.size());
        assertEquals("Name", role.getName());        
    }  
    
    @Test
    void checkRoleWithValidUsers() {
        Role role = new Role();
        role.setName("Name");
        List<User> users=new ArrayList<>(Arrays.asList(new User(),new User()));
        role.setUsers(users);
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertEquals(0, violations.size());
        assertIterableEquals(users, role.getUsers());        
    }
    
    @Test
    void checkToString() {
        Role role = new Role();
        role.setName("Name");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertEquals(0, violations.size());
        String expected="Role {id = 0, name = \'Name\'} ";
        assertEquals(expected, role.toString());        
    }
    
    @Test
    void constraintViolationOnEmptyRoleName() {
        Role emptyRole = new Role();
        emptyRole.setName("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Role>> violations = validator.validate(emptyRole);
        assertEquals(1, violations.size());
    }    
    

}
