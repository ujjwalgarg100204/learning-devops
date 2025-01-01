package com.ujjwalgarg.learningdevops.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Todo {
  @Id
  @GeneratedValue
  private Long id;

  @NotBlank(message = "Title is required")
  @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
  private String title;
  private boolean completed;
}
