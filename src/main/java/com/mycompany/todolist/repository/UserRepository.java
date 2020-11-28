package com.mycompany.todolist.repository;

import com.mycompany.todolist.model.User;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// implement method for retrieve user by email (email is unique - so we have only one user)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {    
    
    public User findByEmail(@Param("email")String email);    
    
    public User findById(@Param("id")long id); 
            
    @Override
    public List<User> findAll();
}
