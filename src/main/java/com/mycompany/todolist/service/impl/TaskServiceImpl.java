package com.mycompany.todolist.service.impl;

import com.mycompany.todolist.exceptions.NullEntityReferenceException;
import com.mycompany.todolist.model.Task;
import com.mycompany.todolist.repository.TaskRepository;
import com.mycompany.todolist.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TaskServiceImpl implements TaskService {
    
    @Autowired
    private TaskRepository taskRepo;    

    @Override    
    public Task create(Task task) {
        if(task==null){
            throw new NullEntityReferenceException(Arrays.asList("Task cannot be 'null'"));
        }        
        taskRepo.save(task);
        return task;
    }

    @Override
    public Task readById(long id) {
        try{
            Optional<Task> task=Optional.of(taskRepo.findById(id));
            return task.get();
        } catch(NullPointerException e){
            throw new EntityNotFoundException("Task with id "+id+" not found");
        }        
    }

    @Override    
    public Task update(Task task) {
        if(task==null){
            throw new NullEntityReferenceException(Arrays.asList("Task cannot be 'null'"));
        }        
        taskRepo.save(task);
        return task;
    }

    @Override    
    public void delete(long id) {        
        try{
            Optional<Task> task=Optional.of(taskRepo.findById(id));
            taskRepo.delete(task.get());
        } catch(NullPointerException e){
            throw new EntityNotFoundException("Task with id "+id+" not found");
        }

    }

    @Override    
    public List<Task> getAll() {        
        return !taskRepo.findAll().isEmpty()?taskRepo.findAll():new ArrayList<Task>();
        
    }

    @Override
    public List<Task> getByTodoId(long todoId) {        
        return !taskRepo.findByToDoId(todoId).isEmpty()?taskRepo.findByToDoId(todoId)
                :new ArrayList<Task>();        
    }
}
