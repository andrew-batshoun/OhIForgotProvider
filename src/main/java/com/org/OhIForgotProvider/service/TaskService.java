package com.org.OhIForgotProvider.service;

import java.util.List;

import com.org.OhIForgotProvider.model.Task;
import com.org.OhIForgotProvider.model.User;

public interface TaskService {
	public Task getTaskById(Long id);
	public Task saveTask(Task task);
	public Task updateTask(Long id, Task task);
	public void deleteTask(Long id);
	public List<Task> listTasks();
	public List<Task> findAllByUserId(Long id);
	
	
}
