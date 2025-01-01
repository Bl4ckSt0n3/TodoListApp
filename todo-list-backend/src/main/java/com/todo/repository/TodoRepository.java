package com.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.api.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer>{

}
