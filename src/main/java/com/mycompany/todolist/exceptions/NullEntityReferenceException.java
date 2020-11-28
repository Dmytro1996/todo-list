/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.exceptions;

import java.util.List;

/**
 *
 * @author dmytr
 */
public class NullEntityReferenceException extends RuntimeException {
    
    private List<String> mistakes;
    
    public NullEntityReferenceException(List<String> mistakes){
        //super(message);
        this.mistakes=mistakes;
    }
    
    public List<String> getMistakes(){
        return mistakes;
    }
}
