package com.mycompany.todolist.service.impl;

import com.mycompany.todolist.exceptions.NullEntityReferenceException;
import com.mycompany.todolist.model.ToDo;
import com.mycompany.todolist.repository.ToDoRepository;
import com.mycompany.todolist.service.ToDoService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;

@Service
public class ToDoServiceImpl implements ToDoService {

    private ToDoRepository todoRepository;

    public ToDoServiceImpl(ToDoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public ToDo create(ToDo todo) {
        try {
            return todoRepository.save(todo);
        } catch (RuntimeException e) {
            throw new NullEntityReferenceException(Arrays.asList("To-Do cannot be 'null'"));
        }
    }

    @Override
    public ToDo readById(long id) {
        Optional<ToDo> optional = Optional.ofNullable(todoRepository.findById(id));
        if (todoRepository.findById(id)!=null) {
            return optional.get();
        }
        throw new EntityNotFoundException("To-Do with id " + id + " not found");
    }

    @Override
    public ToDo update(ToDo todo) {
        if (todo != null) {
            ToDo oldTodo = readById(todo.getId());
            if (oldTodo != null) {
                return todoRepository.save(todo);
            }
        }
        throw new NullEntityReferenceException(Arrays.asList("To-Do cannot be 'null'"));
    }

    @Override    
    public void delete(long id) {
        ToDo todo = readById(id);
        if (todo != null) {
            todoRepository.delete(todo);
        } else {
            throw new EntityNotFoundException("To-Do with id " + id + " not found");
        }
    }

    @Override
    public List<ToDo> getAll() {
        List<ToDo> todos = todoRepository.findAll();
        return todos.isEmpty() ? new ArrayList<>() : todos;
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        List<ToDo> todos = todoRepository.findByOwnerId(userId);
        return todos.isEmpty() ? new ArrayList<>() : todos;
    }
}
