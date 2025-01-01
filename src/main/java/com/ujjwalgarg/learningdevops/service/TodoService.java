package com.ujjwalgarg.learningdevops.service;

import com.ujjwalgarg.learningdevops.model.Todo;
import com.ujjwalgarg.learningdevops.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
  private final TodoRepository repository;

  public List<Todo> getAllTodos() {
    return repository.findAll();
  }

  public Todo createTodo(Todo todo) {
    return repository.save(todo);
  }

  public Optional<Todo> getTodoById(Long id) {
    return repository.findById(id);
  }

  public Todo updateTodo(Long id, Todo todo) {
    todo.setId(id);
    return repository.save(todo);
  }

  public void deleteTodo(Long id) {
    repository.deleteById(id);
  }
}
