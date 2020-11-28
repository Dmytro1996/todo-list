package com.mycompany.todolist.repository;

import com.mycompany.todolist.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findById(long id);

}
