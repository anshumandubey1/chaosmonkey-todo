package com.chaosmonkey.todo.service;

import com.chaosmonkey.todo.dto.TodoRequest;
import com.chaosmonkey.todo.dto.TodoResponse;
import com.chaosmonkey.todo.model.Todo;
import com.chaosmonkey.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TodoServiceTest {

    @MockBean
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @Test
    void shouldAddTodoToDatabaseWhenValidTodoObjectIsGiven() {
        TodoRequest todoRequest = new TodoRequest(
                "To do", "or not to do, there is no try!", LocalDateTime.now().plusDays(4));

        Todo expectedTodo = new Todo(todoRequest);
        doReturn(expectedTodo).when(todoRepository).save(expectedTodo);
        TodoResponse response = todoService.addTodo(todoRequest);

        assertThat(response, is(equalTo(expectedTodo.generateResponse())));
    }

    @Test
    void shouldReturnListOfTodos() {
        Todo todo1 = new Todo(
                new TodoRequest("To do 1", "or not to do 1, there is no try!", LocalDateTime.now().plusDays(1)));
        Todo todo2 = new Todo(
                new TodoRequest("To do 2", "or not to do 2, there is no try!", LocalDateTime.now().plusDays(2)));
        Todo todo3 = new Todo(
                new TodoRequest("To do 3", "or not to do 3, there is no try!", LocalDateTime.now().plusDays(3)));

        List<Todo> expectedList = List.of(todo1, todo2, todo3);
        doReturn(expectedList).when(todoRepository).findAll();
        List<TodoResponse> responses = todoService.getAllTodos();

        assertThat(responses, containsInAnyOrder(expectedList.stream().map(Todo::generateResponse).toArray()));
    }
}
