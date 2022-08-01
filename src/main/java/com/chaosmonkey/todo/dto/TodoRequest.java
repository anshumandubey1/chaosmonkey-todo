package com.chaosmonkey.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TodoRequest {
    private String title;
    private String description;
    private LocalDateTime deadline;
}
