package com.chaosmonkey.todo.controller;

import com.chaosmonkey.todo.dto.ResponseDataObject;
import com.chaosmonkey.todo.dto.TodoRequest;
import com.chaosmonkey.todo.dto.TodoResponse;
import com.chaosmonkey.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @PostMapping("/")
    public ResponseEntity<ResponseDataObject<TodoResponse>> create(@RequestBody TodoRequest todoRequest) {
        TodoResponse todoResponse = todoService.addTodo(todoRequest);
        ResponseDataObject<TodoResponse> response = new ResponseDataObject<>(true, todoResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDataObject<List<TodoResponse>>> list() {
        List<TodoResponse> todoResponses = todoService.getAllTodos();
        ResponseDataObject<List<TodoResponse>> response = new ResponseDataObject<>(true, todoResponses);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataObject<TodoResponse>> update(
            @PathVariable int id, @RequestBody TodoRequest todoRequest
    ) {
        TodoResponse todoResponse = todoService.updateTodo(todoRequest, id);
        ResponseDataObject<TodoResponse> response = new ResponseDataObject<>(true, todoResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        todoService.deleteTodo(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
