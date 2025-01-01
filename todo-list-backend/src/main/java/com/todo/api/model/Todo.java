package com.todo.api.model;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "todo")
public class Todo {

    @Id
    private Integer Id;
    private String Task;
    private Integer Priority; //= 1 | 2 | 3;
    
    public Todo(@Value("") Integer Id, @Value("") String Task, @Value("") Integer Priority) {
        this.Id = Id;
        this.Task = Task;
        this.Priority = Priority;
    }

    public Todo() {

    }

    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public String getTask() {
        return Task;
    }
    public void setTask(String task) {
        Task = task;
    }
    public Integer getPriority() {
        return Priority;
    }
    public void setPriority(Integer priority) {
        Priority = priority;
    }
}
