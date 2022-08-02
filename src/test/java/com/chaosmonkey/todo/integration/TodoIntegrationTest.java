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
}
