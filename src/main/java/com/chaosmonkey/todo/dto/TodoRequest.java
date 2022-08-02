package com.chaosmonkey.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class TodoRequest {
    private String title;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime deadline;
}
