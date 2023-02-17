package com.org.OhIForgotProvider.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.OhIForgotProvider.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
  
	List<Task> findAllByUserId(Long id);
}
