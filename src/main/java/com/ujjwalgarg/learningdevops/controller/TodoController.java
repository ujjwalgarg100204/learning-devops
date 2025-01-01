package com.ujjwalgarg.learningdevops.controller;

import com.ujjwalgarg.learningdevops.model.Todo;
import com.ujjwalgarg.learningdevops.service.TodoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
  private final TodoService service;

  @GetMapping
  public List<Todo> getAllTodos() {
    return service.getAllTodos();
  }

  @PostMapping
  public Todo createTodo(@RequestBody Todo todo) {
    return service.createTodo(todo);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
    return service.getTodoById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
    return ResponseEntity.ok(service.updateTodo(id, todo));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
    service.deleteTodo(id);
    return ResponseEntity.ok().build();
  }
}
