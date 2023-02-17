package com.org.OhIForgotProvider.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.org.OhIForgotProvider.exceptions.NotFoundException;
import com.org.OhIForgotProvider.model.Task;
import com.org.OhIForgotProvider.model.User;
import com.org.OhIForgotProvider.respository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskDao;
	
	
	
	@Override
	public Task getTaskById(Long id) {
		return taskDao.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
	}

	@Override
	public Task saveTask(Task task) {
		
		 return taskDao.saveAndFlush(task);
	}

	@Override
	public Task updateTask(Long id, Task task) {
		
		Task currentTask = getTaskById(id);
		currentTask.setDescription(task.getDescription());
		currentTask.setDueDate(task.getDueDate());
		return taskDao.save(currentTask);
		
	}

	@Override
	public void deleteTask(Long id) {
		System.out.println("Retrieving & Deleting Task with id " + id);
		Task task = getTaskById(id);
		taskDao.deleteById(task.getId());
	}

	@Override
	public List<Task> listTasks() {
		
		return taskDao.findAll();
	}

	@Override
	public List<Task> findAllByUserId(Long id) {
		
		return taskDao.findAllByUserId(id);
	}

}
