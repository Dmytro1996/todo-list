package com.mycompany.todolist.service.impl;

import com.mycompany.todolist.exceptions.NullEntityReferenceException;
import com.mycompany.todolist.model.Role;
import com.mycompany.todolist.repository.RoleRepository;
import com.mycompany.todolist.service.RoleService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    /*public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }*/

    @Override
    public Role create(Role role) {
        try {
            return roleRepository.save(role);
        } catch (IllegalArgumentException e) {
            throw new NullEntityReferenceException(Arrays.asList("Role cannot be 'null'"));
        }
    }

    @Override
    public Role readById(long id) {
        Optional<Role> optional = Optional.of(roleRepository.findById(id));
        return optional.get();
    }

    @Override
    public Role update(Role role) {
        if (role != null) {
            Role oldRole = readById(role.getId());
            if (oldRole != null) {
                return roleRepository.save(role);
            }
        }
        throw new NullEntityReferenceException(Arrays.asList("Role cannot be 'null'"));
    }

    @Override
    public void delete(long id) {        
        /*Role role = readById(id);
        roleRepository.delete(role);        
        if (role != null) {
            roleRepository.delete(role);
        } else {
            throw new NullEntityReferenceException(Arrays.asList("Role cannot be 'null'"));
        }*/
        try{
            roleRepository.delete(readById(id));
        } catch(NullPointerException e){
            throw new NullEntityReferenceException(Arrays.asList("Role with id "+id+" not found"));
        }
    }

    @Override
    public List<Role> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.isEmpty() ? new ArrayList<>() : roles;
    }
}
