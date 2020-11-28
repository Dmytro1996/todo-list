/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.todolist.exceptions;

import java.io.IOException;
import java.util.Arrays;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author dmytr
 */
@ControllerAdvice
public class ApplicationExceptionHandler{
    
    @ExceptionHandler(NullEntityReferenceException.class)
    public ModelAndView NullEntityReferenceException(NullEntityReferenceException ex){
        ModelAndView modelAndView=new ModelAndView("error-page", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("infos", ex.getMistakes());
        return modelAndView;
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(Exception ex){
        ModelAndView modelAndView=new ModelAndView("error-page", HttpStatus.NOT_FOUND);
        modelAndView.addObject("infos", Arrays.asList(ex.getMessage()));
        return modelAndView;
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleAccessDenied(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessEx) throws IOException,ServletException{
        response.sendRedirect("/access-denied");
    }
    
    /*@ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView internalServerErrorHandler(HttpServletRequest request, Exception exception) {
        ModelAndView modelAndView=new ModelAndView("error-page", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("infos", Arrays.asList(exception.getMessage()));
        return modelAndView;
    }*/
    
}
