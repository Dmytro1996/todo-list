package com.mycompany.todolist.repository;


import com.mycompany.todolist.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {    
    ToDo findById(long id);
    List<ToDo> findByOwnerId(long id);
}
