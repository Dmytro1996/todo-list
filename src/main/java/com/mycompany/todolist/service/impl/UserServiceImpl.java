package com.mycompany.todolist.service.impl;

import com.mycompany.todolist.details.UserDetailsImpl;
import com.mycompany.todolist.model.User;
import com.mycompany.todolist.repository.UserRepository;
import com.mycompany.todolist.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {
    
    @Autowired
    private UserRepository userRepo;

    @Override    
    public User create(User user) {       
        userRepo.save(user);                
        return user;
    }

    @Override
    public User readById(long id) {
        try{
            Optional<User> user=Optional.of(userRepo.findById(id));
            return user.get();
        } catch(NullPointerException e){
            throw new EntityNotFoundException("User with id "+id+" not found");
        }
    }

    @Override    
    public User update(User user) {        
        userRepo.save(user);
        return user;
    }

    @Override    
    public void delete(long id) {
        try{
            Optional<User> user=Optional.of(userRepo.findById(id));
            userRepo.delete(user.get());
        } catch(NullPointerException e){
            throw new EntityNotFoundException("User with id "+id+" not found");
        }        
    }

    @Override    
    public List<User> getAll() {
        return userRepo.findAll().isEmpty()?new ArrayList():userRepo.findAll();
    }
    
    @Override
    public UserDetails loadUserByUsername(String userName){        
        Optional<User> user=getAll().stream().filter(u->u.getEmail().equals(userName)).findAny();
        if(user.isPresent()){
            return new UserDetailsImpl(user.get());
        }        
        throw new UsernameNotFoundException("User not found");
    }
}
