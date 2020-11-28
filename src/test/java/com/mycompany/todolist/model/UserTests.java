package com.mycompany.todolist.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import org.junit.jupiter.params.ParameterizedTest;

@SpringBootTest
public class UserTests {

    private static Role mentorRole;
    private static Role traineeRole;
    private static User validUser;

    @BeforeAll
    static void init(){
        mentorRole = new Role();
        mentorRole.setName("MENTOR");
        traineeRole = new Role();
        traineeRole.setName("TRAINEE");
        validUser  = new User();
        validUser.setEmail("valid@cv.edu.ua");
        validUser.setFirstName("Validname");
        validUser.setLastName("Validname");
        validUser.setPassword("qwQW12");
        validUser.setRole(traineeRole);
    }

    @Test
    void userWithValidEmail() {
        User user = new User();
        user.setEmail("rty5@i.ua");
        user.setFirstName("Validname");
        user.setLastName("Validname");
        user.setPassword("qwQW12");
        user.setRole(traineeRole);


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(0, violations.size());
    }

    @Test
    void createValidUser() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(validUser);

        assertEquals(0, violations.size());
    }
    
    @Test
    void userWithValidFirstName() {
        User user = new User();
        user.setEmail("rty5@i.ua");
        user.setFirstName("Validname");
        user.setLastName("Validname");
        user.setPassword("qwQW12");
        user.setRole(traineeRole);


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(0, violations.size());
        assertEquals("Validname",user.getFirstName());
    }
    
    @Test
    void userWithValidLastName() {
        User user = new User();
        user.setEmail("rty5@i.ua");
        user.setFirstName("Validname");
        user.setLastName("Validname");
        user.setPassword("qwQW12");
        user.setRole(traineeRole);


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(0, violations.size());
        assertEquals("Validname",user.getLastName());
    }
    
    @Test
    void userWithValidPassword() {
        User user = new User();
        user.setEmail("rty5@i.ua");
        user.setFirstName("Validname");
        user.setLastName("Validname");
        user.setPassword("qwQW12");
        user.setRole(traineeRole);


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(0, violations.size());
        assertEquals("qwQW12",user.getPassword());
    }
    
    @Test
    void userWithValidRole() {
        User user = new User();
        user.setEmail("rty5@i.ua");
        user.setFirstName("Validname");
        user.setLastName("Validname");
        user.setPassword("qwQW12");
        user.setRole(traineeRole);


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(0, violations.size());
        assertEquals(traineeRole.toString(),user.getRole().toString());
    }
    
    void userWithValidToDo() {
        User user = new User();
        user.setEmail("rty5@i.ua");
        user.setFirstName("Validname");
        user.setLastName("Validname");
        user.setPassword("qwQW12");
        user.setRole(traineeRole);
        List<ToDo> todos=new ArrayList<>(Arrays.asList(new ToDo(),new ToDo()));
        user.setMyTodos(todos);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(0, violations.size());
        assertIterableEquals(todos,user.getMyTodos());
    }
    
    @ParameterizedTest
    @MethodSource("provideInvalidEmailUser")
    void constraintViolationInvalid(String input, String errorValue) {
        User user = new User();
        user.setEmail(input);
        user.setFirstName("Validname");
        user.setLastName("Validname");
        user.setPassword("qwQW12");
        user.setRole(traineeRole);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidEmailUser(){
        return Stream.of(
                Arguments.of("invalidEmail", "invalidEmail"),
                Arguments.of("email@", "email@"),
                Arguments.of("", ""),
                Arguments.of("invalid", "invalid")
        );
    }
    
    @ParameterizedTest
    @MethodSource("provideInvalidFirstNameUser")
    void constraintViolationInvalidFirstName(String input, String errorValue) {
        User user = new User();
        user.setEmail(validUser.getEmail());
        user.setFirstName(input);
        user.setLastName("Validname");
        user.setPassword("qwQW12");
        user.setRole(traineeRole);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidFirstNameUser(){
        return Stream.of(
                Arguments.of("invalid", "invalid"),
                Arguments.of("Invalid-", "Invalid-"),
                Arguments.of("Invalid-invalid", "Invalid-invalid")
        );
    }

    @ParameterizedTest
    @MethodSource("provideToDos")
    void checkSetTodos(List<ToDo> input) {
        User user = new User();
        user.setEmail(validUser.getEmail());
        user.setFirstName("Validname");
        user.setLastName("Validname");
        user.setPassword("qwQW12");
        user.setRole(traineeRole);
        user.setMyTodos(input);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());        
    }

    private static Stream<Arguments> provideToDos(){                
        return Stream.of(
                Arguments.of(Arrays.asList(new ToDo()))                
        );
    }
    
    @ParameterizedTest
    @MethodSource("provideInvalidFirstNameUser")
    void constraintViolationInvalidLastName(String input, String errorValue) {
        User user = new User();
        user.setEmail(validUser.getEmail());
        user.setFirstName("Validname");
        user.setLastName(input);
        user.setPassword("qwQW12");
        user.setRole(traineeRole);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }
    
    @ParameterizedTest
    @MethodSource("provideInvalidEmail")
    void constraintViolationInvalidEmail(String input, String errorValue) {
        User user = new User();
        user.setEmail(input);
        user.setFirstName("Validname");
        user.setLastName("Validname");
        user.setPassword("qwQW12");
        user.setRole(traineeRole);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidEmail(){
        return Stream.of(
                Arguments.of("myemail.com","myemail.com"),
                Arguments.of("my.mail@", "my.mail@"),
                Arguments.of("@.com", "@.com")
        );
    }
    
    @ParameterizedTest
    @MethodSource("provideInvalidPassword")
    void constraintViolationInvalidPassword(String input, String errorValue) {
        User user = new User();
        user.setEmail(validUser.getEmail());
        user.setFirstName("Validname");
        user.setLastName("Validname");
        user.setPassword(input);
        user.setRole(traineeRole);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidPassword(){
        return Stream.of(
                Arguments.of("#123)", "#123)"),
                Arguments.of("uy46,?", "uy46,?"),
                Arguments.of("/,^&a", "/,^&a")
        );
    }     

}
