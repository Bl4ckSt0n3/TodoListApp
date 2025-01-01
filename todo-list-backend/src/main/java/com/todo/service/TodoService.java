package com.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.api.model.ResponseModel;
import com.todo.api.model.Todo;
import com.todo.repository.TodoRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class TodoService {


    @Autowired
    TodoRepository todoRepo;

    private List<Todo> todoList;

    public TodoService() {
        todoList = new ArrayList<>();
        Todo todo1 = new Todo(1, "Implement loading - frontend only", 1);
        Todo todo2 = new Todo(2, "Implement search - frontend only", 2);
        Todo todo3 = new Todo(3, "Implement delete on click - frontend only", 1);
        Todo todo4 = new Todo(4, "Replace mock service by integrating backend",3);

        todoList.addAll(Arrays.asList(todo1, todo2, todo3, todo4)); // mock todo list
    }


    // obsolete method
    public ArrayList<Todo> getAllTodo() {
        return (ArrayList<Todo>) todoList;
    }


    /**
     * in-memory DB methods
     */
    public ResponseModel<String> addTodo(Todo todo) {
        ResponseModel<String> response = new ResponseModel<String>();
        try {

            // check priority 
            if (todo.getPriority() < 1 && todo.getPriority() > 3) 
            {
                response.Data = null;
                response.Message = "Priority cannot be less than 1 and greater than 3";
                response.isSuccess = false;
                return response;
            }

            List<Todo> todoList = todoRepo.findAll();
            Todo todoModel = new Todo();
            todoModel.setId(todoList.size() + 1);
            todoModel.setPriority(todo.getPriority());
            todoModel.setTask(todo.getTask());

            todoRepo.save(todoModel);
            
            response.Data = "todo added";
            response.Message = "Success";
            response.isSuccess = true;
            return response;
        }
        catch (Exception ex) {
            response.Data = null;
            response.Message = ex.getMessage();
            response.isSuccess = false;
            return response;
        }
    }

    public ResponseModel<List<Todo>> getList() {
        ResponseModel<List<Todo>> response = new ResponseModel<List<Todo>>();
        try {
            List<Todo> todoList = todoRepo.findAll();
            response.Data = todoList;
            response.Message = "Success";
            response.isSuccess = true;
            return response;
        }
        catch (Exception ex) {
            response.Data = null;
            response.Message = ex.getMessage();
            response.isSuccess = false;
            // List<Todo> emptyTodo = new ArrayList<>();
            return response;
        } 
    }

    public ResponseModel<Todo> getById(int Id) {
        ResponseModel<Todo> response = new ResponseModel<Todo>();
        try {
            response.Data = todoRepo.findById(Id).orElseThrow();
            response.Message = "Success";
            response.isSuccess = true;
            return response;
        }
        catch (Exception ex) {
            response.Data = null;
            response.Message = ex.getMessage();
            response.isSuccess = false;
            return response;
        }
    }
    public ResponseModel<String> deleteTodoById(int Id) {
        ResponseModel<String> response = new ResponseModel<String>();
        try {
            todoRepo.deleteById(Id);
            response.Data = "todo has been removed";
            response.Message = "Success";
            response.isSuccess = true;
            return response;
        }
        catch (Exception ex) {
            response.Data = null;
            response.Message = ex.getMessage();
            response.isSuccess = false;
            return response;
        }
    }
}
