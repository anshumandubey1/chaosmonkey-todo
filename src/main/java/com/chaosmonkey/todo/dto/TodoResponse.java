package com.chaosmonkey.todo.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class TodoResponse {
    private int id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private LocalDateTime created_at;
    private LocalDateTime completed_at;
}
