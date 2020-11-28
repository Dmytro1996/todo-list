package com.mycompany.todolist.repository;

import com.mycompany.todolist.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    State findById(long id);
    State findByName(String name);
}
