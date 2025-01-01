package com.ujjwalgarg.learningdevops.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Todo {
  @Id
  @GeneratedValue
  private Long id;
  private String title;
  private boolean completed;
}
