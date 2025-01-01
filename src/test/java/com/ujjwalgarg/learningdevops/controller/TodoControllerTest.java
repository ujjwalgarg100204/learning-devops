package com.ujjwalgarg.learningdevops.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ujjwalgarg.learningdevops.model.Todo;
import com.ujjwalgarg.learningdevops.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TodoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private TodoRepository todoRepository;

  private Todo testTodo;

  @BeforeEach
  void setUp() {
    todoRepository.deleteAll();

    testTodo = new Todo();
    testTodo.setTitle("Test Todo");
    testTodo.setCompleted(false);
    testTodo = todoRepository.save(testTodo);
  }

  @Test
  void getAllTodos_ShouldReturnAllTodosFromDatabase() throws Exception {
    mockMvc.perform(get("/api/todos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].title", is("Test Todo")))
        .andExpect(jsonPath("$[0].completed", is(false)));
  }

  @Test
  void createTodo_ShouldPersistTodoAndReturnCreatedTodo() throws Exception {
    Todo newTodo = new Todo();
    newTodo.setTitle("New Todo");
    newTodo.setCompleted(false);

    mockMvc.perform(post("/api/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newTodo)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is("New Todo")))
        .andExpect(jsonPath("$.id", notNullValue()));
  }

  @Test
  void getTodoById_WhenExists_ShouldReturnTodo() throws Exception {
    mockMvc.perform(get("/api/todos/{id}", testTodo.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is(testTodo.getTitle())));
  }

  @Test
  void getTodoById_WhenNotExists_ShouldReturn404() throws Exception {
    mockMvc.perform(get("/api/todos/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateTodo_ShouldUpdateExistingTodo() throws Exception {
    testTodo.setTitle("Updated Todo");
    testTodo.setCompleted(true);

    mockMvc.perform(put("/api/todos/{id}", testTodo.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testTodo)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is("Updated Todo")))
        .andExpect(jsonPath("$.completed", is(true)));
  }

  @Test
  void deleteTodo_ShouldRemoveTodoFromDatabase() throws Exception {
    mockMvc.perform(delete("/api/todos/{id}", testTodo.getId()))
        .andExpect(status().isOk());

    mockMvc.perform(get("/api/todos/{id}", testTodo.getId()))
        .andExpect(status().isNotFound());
  }

  @Test
  void createTodo_WithBlankTitle_ShouldReturn400() throws Exception {
    Todo invalidTodo = new Todo();
    invalidTodo.setTitle("");

    mockMvc.perform(post("/api/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidTodo)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void createTodo_WithShortTitle_ShouldReturn400() throws Exception {
    Todo invalidTodo = new Todo();
    invalidTodo.setTitle("ab");

    mockMvc.perform(post("/api/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidTodo)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title", containsString("between 3 and 100")));
  }

  @Test
  void updateTodo_WithInvalidTitle_ShouldReturn400() throws Exception {
    testTodo.setTitle("");

    mockMvc.perform(put("/api/todos/{id}", testTodo.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testTodo)))
        .andExpect(status().isBadRequest());
  }
}
