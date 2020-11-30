package com.mycompany.todolist.controllers;

import com.mycompany.todolist.model.Priority;
import com.mycompany.todolist.model.Task;
import com.mycompany.todolist.service.StateService;
import com.mycompany.todolist.service.TaskService;
import com.mycompany.todolist.service.ToDoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final ToDoService todoService;
    private final StateService stateService;
    private Logger logger=LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService taskService, ToDoService todoService, StateService stateService) {
        this.taskService = taskService;
        this.todoService = todoService;
        this.stateService = stateService;
    }

    @GetMapping("/create/todos/{todo_id}")
    public String create(@PathVariable("todo_id") long todoId, Model model) {        
        model.addAttribute("task", new Task());
        model.addAttribute("todo", todoService.readById(todoId));
        model.addAttribute("priorities", Priority.values());
        return "create-task";
    }

    @PostMapping("/create/todos/{todo_id}")
    public String create(@PathVariable("todo_id") long todoId, Model model,
                         @Validated @ModelAttribute("task") Task task, BindingResult result) {        
        if (result.hasErrors()) {
            model.addAttribute("todo", todoService.readById(todoId));
            model.addAttribute("priorities", Priority.values());
            return "create-task";
        }        
        task.setTodo(todoService.readById(todoId));
        task.setState(stateService.getByName("New"));        
        taskService.create(task);        
        return "redirect:/todos/" + todoId + "/tasks";
    }

    @GetMapping("/{task_id}/update/todos/{todo_id}")
    public String update(@PathVariable("task_id") long taskId, @PathVariable("todo_id") long todoId, Model model) {        
        Task task=taskService.readById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("stateId",task.getState().getId());
        model.addAttribute("todoId",todoId);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("states", stateService.getAll());
        return "update-task";
    }

    @PostMapping("/{task_id}/update/todos/{todo_id}")
    public String update(@PathVariable("task_id") long taskId, @PathVariable("todo_id") long todoId, Model model,
                         @Validated @ModelAttribute("task")Task task, BindingResult result) {
        logger.info("Before result.hasErrors()");
        if (result.hasErrors()) {
            model.addAttribute("stateId",task.getState().getId());
            model.addAttribute("todoId",todoId);
            model.addAttribute("priorities", Priority.values());
            model.addAttribute("states", stateService.getAll());
            logger.info("Before update-task");
            return "update-task";
        }        
        logger.info("Before task.setTodo");
        task.setTodo(todoService.readById(todoId));
        logger.info("Before taskService.update()");
        //task.setState(stateService.readById(task.getState().getId()));
        taskService.update(task);
        logger.info("Before redirect");
        return "redirect:/todos/" + todoId + "/tasks";
    }

    @GetMapping("/{task_id}/delete/todos/{todo_id}")
    public String delete(@PathVariable("task_id") long taskId, @PathVariable("todo_id") long todoId) {
        taskService.delete(taskId);
        return "redirect:/todos/" + todoId + "/tasks";
    }
}
