package com.chaosmonkey.todo.service;

import com.chaosmonkey.todo.dto.TodoRequest;
import com.chaosmonkey.todo.dto.TodoResponse;
import com.chaosmonkey.todo.model.Todo;
import com.chaosmonkey.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    public TodoResponse addTodo(TodoRequest todoRequest) {
        Todo todo = new Todo(todoRequest);
        Todo savedTodo = todoRepository.save(todo);
        return savedTodo.generateResponse();
    }

    public List<TodoResponse> getAllTodos() {
        List<TodoResponse> responses = new ArrayList<>();
        todoRepository.findAll().forEach(todo -> {
            responses.add(todo.generateResponse());
        });
        return responses;
    }
}
