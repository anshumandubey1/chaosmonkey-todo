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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class TodoControllerTest {

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

    @Test
    void shouldReturnListOfTodos() {
        TodoResponse todo1 = new TodoResponse(
                1, "To do 1", "or not to do 1, there is no try!", LocalDateTime.now().plusDays(1), LocalDateTime.now(),
                null
        );
        TodoResponse todo2 = new TodoResponse(
                1, "To do 2", "or not to do 2, there is no try!", LocalDateTime.now().plusDays(2), LocalDateTime.now(),
                null
        );
        TodoResponse todo3 = new TodoResponse(
                1, "To do 3", "or not to do 3, there is no try!", LocalDateTime.now().plusDays(3), LocalDateTime.now(),
                null
        );

        List<TodoResponse> expectedList = List.of(todo1, todo2, todo3);
        doReturn(expectedList).when(todoService).getAllTodos();
        ResponseEntity<ResponseDataObject<List<TodoResponse>>> response = todoController.list();

        assertThat(response.getBody().getData(), containsInAnyOrder(expectedList.toArray()));
    }

    @Test
    void shouldUpdateATodoWhenValidTodoObjectAndValidTodoIdIsGiven() {
        int id = 1;
        TodoRequest todoRequest = new TodoRequest(
                "To do again", "or not to do again, there is no try!", LocalDateTime.now().plusDays(4));


        TodoResponse expectedTodo = new Todo(todoRequest).generateResponse();
        doReturn(expectedTodo).when(todoService).updateTodo(todoRequest, id);
        ResponseEntity<ResponseDataObject<TodoResponse>> response = todoController.update(id, todoRequest);

        assertThat(response.getBody().getData(), is(equalTo(expectedTodo)));
    }
}
