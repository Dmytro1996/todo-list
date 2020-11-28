package com.mycompany.todolist.repository;

import com.mycompany.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

// TODO
// implements methods for retrieving Tasks by todo_id
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    public Task findById(@Param("id")long id);
        
    @Override        
    public List<Task> findAll();    
    
    @Query(value="select * from Tasks t where t.todo_id=:todoId",nativeQuery=true)
    public List<Task> findByToDoId(@Param("todoId")long todoId);
}
