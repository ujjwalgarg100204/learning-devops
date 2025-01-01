package com.ujjwalgarg.learningdevops.repository;

import com.ujjwalgarg.learningdevops.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
