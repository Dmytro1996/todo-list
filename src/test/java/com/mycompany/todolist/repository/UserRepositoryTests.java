/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.repository;

import com.mycompany.todolist.model.User;
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
public class UserRepositoryTests {    
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    public void getByUserEmailTest(){
        User user=new User();
        user.setFirstName("John");
        user.setLastName("Johnson");
        user.setEmail("johnjohnson@email.com");
        user.setPassword("Aa12345678");        
        user.setRole(userRepository.findById(5L).getRole());
        entityManager.persist(user);               
        assertEquals(user.getFirstName(),userRepository.findByEmail(
                user.getEmail()).getFirstName());
        assertEquals(user.getLastName(),userRepository.findByEmail(
                user.getEmail()).getLastName());
        assertEquals(user.getEmail(),userRepository.findByEmail(
                user.getEmail()).getEmail());
    }
    
}
