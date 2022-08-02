package com.chaosmonkey.todo.controller;

import com.chaosmonkey.todo.dto.ResponseDataObject;
import com.chaosmonkey.todo.dto.TodoRequest;
import com.chaosmonkey.todo.dto.TodoResponse;
import com.chaosmonkey.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @PostMapping("/")
    public ResponseEntity<ResponseDataObject<TodoResponse>> create(TodoRequest todoRequest) {
        TodoResponse todoResponse = todoService.addTodo(todoRequest);
        ResponseDataObject<TodoResponse> response = new ResponseDataObject<>(true, todoResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
