package com.chaosmonkey.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDataObject<T> {
    private boolean success;
    private T data;
}
