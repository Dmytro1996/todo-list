/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.service;

import com.mycompany.todolist.exceptions.NullEntityReferenceException;
import com.mycompany.todolist.model.Role;
import com.mycompany.todolist.repository.RoleRepository;
import com.mycompany.todolist.service.impl.RoleServiceImpl;
import java.util.Arrays;
import java.util.List;
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
public class RoleServiceTests {
       
    @TestConfiguration
    static class UserServiceTestConfiguration{
        @Bean
        public RoleService roleService(){
            return new RoleServiceImpl();
        }        
    }
    
    @Autowired
    private RoleService roleService;   
        
    @MockBean
    private RoleRepository roleRepository;
    
    private Role role;
    
    @BeforeEach
    public void setUp(){        
        role=new Role();        
        role.setName("Developer");        
        Mockito.when(roleRepository.findById(7L)).thenReturn(role);
        Mockito.when(roleRepository.findById(0L)).thenReturn(role);               
        Mockito.when(roleRepository.save(role)).thenReturn(role);
        Mockito.when(roleRepository.save(null)).thenThrow(
                new NullEntityReferenceException(Arrays.asList("Role cannot be 'null'")));
        Mockito.when(roleRepository.findAll()).thenReturn(Arrays.asList(role));
    }

    @Test
    public void readByIdTestWithMock(){       
        assertEquals(role,roleService.readById(7L));        
    }

    @Test
    public void createTest(){
        assertEquals(role.toString(),roleService.create(role).toString());        
        NullEntityReferenceException e=assertThrows(NullEntityReferenceException.class,()->{roleService.update(null);});
        assertEquals(Arrays.asList("Role cannot be 'null'").toString(),e.getMistakes().toString());
    }

    @Test
    public void updateTest(){
        assertEquals(role.toString(),roleService.update(role).toString());        
        NullEntityReferenceException e=assertThrows(NullEntityReferenceException.class,()->{roleService.update(null);});
        assertEquals(Arrays.asList("Role cannot be 'null'").toString(),e.getMistakes().toString());
    }    
    
    @Test
    public void getAllTest(){
        List<Role> rolesExpected=Arrays.asList(role);
        List<Role> rolesActual=roleService.getAll();
        assertEquals(rolesExpected.toString(),rolesActual.toString());
    }

    @Test
    public void deleteTest(){
        Throwable e=assertThrows(NullEntityReferenceException.class,()->{roleService.delete(6L);});
         assertEquals(NullEntityReferenceException.class.getName(),e.getClass().getName());
    }    
    
}


