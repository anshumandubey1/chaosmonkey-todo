package com.chaosmonkey.todo.service;

import com.chaosmonkey.todo.dto.TodoRequest;
import com.chaosmonkey.todo.dto.TodoResponse;
import com.chaosmonkey.todo.model.Todo;
import com.chaosmonkey.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    public TodoResponse addTodo(TodoRequest todoRequest) {
        Todo todo = new Todo(todoRequest);
        Todo savedTodo = todoRepository.save(todo);
        return savedTodo.generateResponse();
    }
}
