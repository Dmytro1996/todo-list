package com.mycompany.todolist.service.impl;

import com.mycompany.todolist.exceptions.NullEntityReferenceException;
import com.mycompany.todolist.model.State;
import com.mycompany.todolist.repository.StateRepository;
import com.mycompany.todolist.service.StateService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StateServiceImpl implements StateService {
    private StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State create(State state) {
        try {
            return stateRepository.save(state);
        } catch (IllegalArgumentException e) {
            throw new NullEntityReferenceException(Arrays.asList("State cannot be 'null'"));
        }
    }

    @Override
    public State readById(long id) {
        Optional<State> optional = Optional.ofNullable(stateRepository.findById(id));
        if (stateRepository.findById(id)!=null) {
            return optional.get();
        }
        throw new EntityNotFoundException("State with id " + id + " not found");
    }

    @Override
    public State update(State state) {
        if (state != null) {
            State oldState = readById(state.getId());
            if (oldState != null) {
                return stateRepository.save(state);
            }
        }
        throw new NullEntityReferenceException(Arrays.asList("State cannot be 'null'"));
    }

    @Override
    public void delete(long id) {
        State state = readById(id);
        if (state != null) {
            stateRepository.delete(state);
        } else {
            throw new EntityNotFoundException("State with id " + id + " not found");
        }
    }

    @Override
    public State getByName(String name) {
        Optional<State> optional = Optional.ofNullable(stateRepository.findByName(name));
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException("State with name '" + name + "' not found");
    }

    @Override
    public List<State> getAll() {
        List<State> states = stateRepository.findAll();
        return states.isEmpty() ? new ArrayList<>() : states;
    }
    
    @Override
    public List<State> getSortAsc(){
        return getAll().stream().sorted((s1,s2)->{return (int)(s1.getId()-s2.getId());})
                .collect(Collectors.toList());
    }
}
