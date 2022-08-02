package com.chaosmonkey.todo.model;

import com.chaosmonkey.todo.dto.TodoRequest;
import com.chaosmonkey.todo.dto.TodoResponse;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "todos")
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL PRIMARY KEY")
    private int id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @Column(columnDefinition = "TIMESTAMP NOT NULL CHECK(deadline > created_at)")
    private LocalDateTime deadline;
    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;
    private LocalDateTime completed_at;

    public Todo(TodoRequest todoRequest) {
        update(todoRequest);
    }

    public TodoResponse generateResponse() {
        return new TodoResponse(id, title, description, deadline, created_at, completed_at);
    }

    public void update(TodoRequest todoRequest) {
        title = todoRequest.getTitle();
        description = todoRequest.getDescription();
        deadline = todoRequest.getDeadline();
    }
}
