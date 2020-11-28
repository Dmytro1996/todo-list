/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.repository;

import com.mycompany.todolist.model.State;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
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
public class StateRepositoryTests {
    
    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    public void getByNameTest(){
        State state=new State();
        state.setName("In progress");
        entityManager.persist(state);
        assertEquals(state.toString(),stateRepository.findByName("In progress").toString());
    }
    
    @Test
    public void getAllTest(){
        int sizeBefore=stateRepository.findAll().size();
        State stateInProgress=new State();
        stateInProgress.setName("In progress");
        State stateFailed=new State();
        stateFailed.setName("Failed");
        entityManager.persist(stateInProgress);
        entityManager.persist(stateFailed);        
        assertEquals(sizeBefore+2,stateRepository.findAll().size());        
        /*assertIterableEquals(stateRepository.findAll().stream().map(s->s.getId())
                .sorted((id1,id2)->{return (int)(id1-id2);}).collect(Collectors.toList()),
                stateRepository.findAll().stream().map(s->s.getId()).collect(Collectors.toList()));*/
    }
    
}
