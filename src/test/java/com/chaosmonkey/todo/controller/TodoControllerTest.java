package com.chaosmonkey.todo.controller;

import com.chaosmonkey.todo.dto.ResponseDataObject;
import com.chaosmonkey.todo.dto.TodoRequest;
import com.chaosmonkey.todo.dto.TodoResponse;
import com.chaosmonkey.todo.model.Todo;
import com.chaosmonkey.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class TodoControllerTest {

    @MockBean
    private TodoService todoService;

    @Autowired
    private TodoController todoController;

    @Test
    void shouldAddTodoToDatabaseWhenValidTodoObjectIsGiven() {
        TodoRequest todoRequest = new TodoRequest(
                "To do", "or not to do, there is no try!", LocalDateTime.now().plusDays(4));

        TodoResponse expectedTodoResponse = new Todo(todoRequest).generateResponse();
        doReturn(expectedTodoResponse).when(todoService).addTodo(todoRequest);
        ResponseEntity<ResponseDataObject<TodoResponse>> response = todoController.create(todoRequest);

        assertThat(response.getBody().getData(), is(equalTo(expectedTodoResponse)));
    }
}
