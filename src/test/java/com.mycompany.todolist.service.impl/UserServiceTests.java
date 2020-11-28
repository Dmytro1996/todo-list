/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.service;

import com.mycompany.todolist.exceptions.NullEntityReferenceException;
import com.mycompany.todolist.model.Role;
import com.mycompany.todolist.model.User;
import com.mycompany.todolist.repository.UserRepository;
import com.mycompany.todolist.service.impl.UserServiceImpl;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author dmytr
 */
@ExtendWith(SpringExtension.class)
public class UserServiceTests {
    
    @TestConfiguration
    static class UserServiceTestConfiguration{
        @Bean
        public UserService userService(){
            return new UserServiceImpl();
        }        
    }
    
    @Autowired
    private UserService userService;   
        
    @MockBean
    private UserRepository userRepository;
    
    private User user;
    
    @BeforeEach
    public void setUp(){
        user=new User();
        user.setFirstName("James");
        user.setLastName("Smith");
        user.setEmail("jamessmith@mail.com");
        user.setPassword("Aa12345678@");
        Role role=new Role();
        role.setName("Developer");
        user.setRole(role);
        user.setId(7);
        Mockito.when(userRepository.findById(7L)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.save(null)).thenThrow(
                new NullEntityReferenceException(Arrays.asList("User cannot be 'null'")));
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
    }

    @Test
    public void readByIdTestWithMock(){        
        long actualId=userService.readById(7L).getId();
        assertEquals(7,actualId);
        Throwable e=assertThrows(EntityNotFoundException.class,()->{userService.readById(6L);});
        assertEquals("User with id 6 not found",e.getMessage());
    }

    @Test
    public void createTest(){
        assertEquals(user.getId(),userService.create(user).getId());
        assertEquals(user.getFirstName(),userService.create(user).getFirstName());
        assertEquals(user.getLastName(),userService.create(user).getLastName());
        assertEquals(user.getEmail(),userService.create(user).getEmail());
        NullEntityReferenceException e=assertThrows(NullEntityReferenceException.class,()->{userService.create(null);});
        assertEquals(Arrays.asList("User cannot be 'null'").toString(),e.getMistakes().toString());
    }

    @Test
    public void updateTest(){
        assertEquals(user.getId(),userService.update(user).getId());
        assertEquals(user.getFirstName(),userService.update(user).getFirstName());
        assertEquals(user.getLastName(),userService.update(user).getLastName());
        assertEquals(user.getEmail(),userService.update(user).getEmail());
        NullEntityReferenceException e=assertThrows(NullEntityReferenceException.class,()->{userService.update(null);});
        assertEquals(Arrays.asList("User cannot be 'null'").toString(),e.getMistakes().toString());
    }    
    
    @Test
    public void getAllTest(){
        List<User> usersExpected=Arrays.asList(user);
        List<User> usersActual=userService.getAll();
        assertEquals(usersExpected.toString(),usersActual.toString());
    }

    @Test
    public void deleteTest(){
        Throwable e=assertThrows(EntityNotFoundException.class,()->{userService.delete(6L);});
        assertEquals("User with id 6 not found",e.getMessage());
    }     
}
