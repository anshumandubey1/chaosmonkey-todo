package com.chaosmonkey.todo.integration;

import com.chaosmonkey.todo.controller.TodoController;
import com.chaosmonkey.todo.dto.ResponseDataObject;
import com.chaosmonkey.todo.dto.TodoRequest;
import com.chaosmonkey.todo.dto.TodoResponse;
import com.chaosmonkey.todo.model.Todo;
import com.chaosmonkey.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(TodoController.class)
@ExtendWith(SpringExtension.class)
public class TodoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    void shouldAddTodoToDatabaseWhenValidTodoObjectIsGiven() throws Exception {
        TodoRequest todoRequest = new TodoRequest(
                "To do", "or not to do, there is no try!", LocalDateTime.now().plusDays(4));
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String requestBody = mapper.writeValueAsString(todoRequest);
        TodoResponse expectedTodoResponse = new Todo(todoRequest).generateResponse();
        ResponseDataObject<TodoResponse> expectedResponse = new ResponseDataObject<>(true, expectedTodoResponse);
        String responseBody = mapper.writeValueAsString(expectedResponse);

        doReturn(expectedTodoResponse).when(todoService).addTodo(todoRequest);

        mockMvc.perform(
                       MockMvcRequestBuilders.post("/todo/").contentType(MediaType.APPLICATION_JSON).content(requestBody)
                                             .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
               .andExpect(content().string(responseBody));
    }

    @Test
    void shouldReturnListOfTodos() throws Exception {
        TodoResponse todo1 = new TodoResponse(1, "To do 1", "or not to do 1, there is no try!",
                                              LocalDateTime.now().plusDays(1), LocalDateTime.now(), null
        );
        TodoResponse todo2 = new TodoResponse(2, "To do 2", "or not to do 2, there is no try!",
                                              LocalDateTime.now().plusDays(2), LocalDateTime.now(), null
        );
        TodoResponse todo3 = new TodoResponse(3, "To do 3", "or not to do 3, there is no try!",
                                              LocalDateTime.now().plusDays(3), LocalDateTime.now(), null
        );
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        List<TodoResponse> expectedList = List.of(todo1, todo2, todo3);
        ResponseDataObject<List<TodoResponse>> response = new ResponseDataObject<>(true, expectedList);
        String responseBody = mapper.writeValueAsString(response);

        doReturn(expectedList).when(todoService).getAllTodos();

        mockMvc.perform(MockMvcRequestBuilders.get("/todo/").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk()).andExpect(content().string(responseBody));
    }
}
