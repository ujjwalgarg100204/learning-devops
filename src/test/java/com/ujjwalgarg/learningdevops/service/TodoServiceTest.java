package com.ujjwalgarg.learningdevops.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;

import com.ujjwalgarg.learningdevops.model.Todo;
import com.ujjwalgarg.learningdevops.repository.TodoRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TodoServiceTest {

  @Mock
  private TodoRepository repository;

  @InjectMocks
  private TodoService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllTodos() {
    Todo todo1 = new Todo();
    todo1.setId(1L);
    todo1.setTitle("Test Todo 1");

    Todo todo2 = new Todo();
    todo2.setId(2L);
    todo2.setTitle("Test Todo 2");

    when(repository.findAll()).thenReturn(Arrays.asList(todo1, todo2));

    List<Todo> todos = service.getAllTodos();
    assertEquals(2, todos.size());
    assertEquals("Test Todo 1", todos.get(0).getTitle());
    assertEquals("Test Todo 2", todos.get(1).getTitle());
  }

  @Test
  void testCreateTodo() {
    Todo todo = new Todo();
    todo.setTitle("New Todo");

    when(repository.save(todo)).thenReturn(todo);

    Todo createdTodo = service.createTodo(todo);
    assertNotNull(createdTodo);
    assertEquals("New Todo", createdTodo.getTitle());
  }

  @Test
  void testGetTodoById() {
    Todo todo = new Todo();
    todo.setId(1L);
    todo.setTitle("Test Todo");

    when(repository.findById(1L)).thenReturn(Optional.of(todo));

    Optional<Todo> foundTodo = service.getTodoById(1L);
    assertTrue(foundTodo.isPresent());
    assertEquals("Test Todo", foundTodo.get().getTitle());
  }

  @Test
  void testUpdateTodo() {
    Todo todo = new Todo();
    todo.setId(1L);
    todo.setTitle("Updated Todo");

    when(repository.save(todo)).thenReturn(todo);

    Todo updatedTodo = service.updateTodo(1L, todo);
    assertNotNull(updatedTodo);
    assertEquals("Updated Todo", updatedTodo.getTitle());
  }

  @Test
  void testDeleteTodo() {
    doNothing().when(repository).deleteById(1L);

    service.deleteTodo(1L);

    verify(repository, times(1)).deleteById(1L);
  }
}
