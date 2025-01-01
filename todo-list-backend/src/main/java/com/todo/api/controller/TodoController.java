package com.todo.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.api.model.ResponseModel;
import com.todo.api.model.Todo;
import com.todo.service.TodoService;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // @CrossOrigin(origins = "https://localhost:4200")
    @GetMapping("/getAll")
    public ArrayList<Todo> getAll() {
        return todoService.getAllTodo();
    }

    // @CrossOrigin(origins = "https://localhost:4200")
    @GetMapping("/getById")
    public ResponseModel<Todo> getById(@RequestParam Integer id) {
        return todoService.getById(id);
    }

    // GET BY ID
    @CrossOrigin(origins = "https://localhost:4200")
    @GetMapping("/getList")
    public ResponseModel<List<Todo>> getList() {
        return todoService.getList();
    }

    // CREATE
    @CrossOrigin(origins = "https://localhost:4200")
    @PostMapping("/addTodo")
    public ResponseModel<String> addTodo(@RequestBody Todo todo) {
        return todoService.addTodo(todo);
    }

    // DELETE 
    @CrossOrigin(origins = "https://localhost:4200")
    @DeleteMapping("/deleteTodo")
    public ResponseModel<String> deleteTodo(@RequestParam Integer id) {
        return todoService.deleteTodoById(id);
    }
}
