package com.chaosmonkey.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidIdException extends ResponseStatusException {
    public InvalidIdException() {
        super(HttpStatus.NOT_FOUND, "No entity found with given Id");
    }
}
