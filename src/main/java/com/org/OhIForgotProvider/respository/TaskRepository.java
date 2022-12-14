package com.org.OhIForgotProvider.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.OhIForgotProvider.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
  
}
