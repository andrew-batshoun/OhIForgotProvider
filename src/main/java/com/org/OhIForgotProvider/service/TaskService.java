package com.org.OhIForgotProvider.service;

import java.util.List;

import com.org.OhIForgotProvider.model.Task;

public interface TaskService {
	public Task findTaskById(Long id);
	public void saveTask(Task task);
	public void updateTask(Task task);
	public void deleteTask(Long id);
	public List<Task> listTasks();
	
}
