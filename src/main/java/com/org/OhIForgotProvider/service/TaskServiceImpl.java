package com.org.OhIForgotProvider.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.OhIForgotProvider.model.Task;
import com.org.OhIForgotProvider.respository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskDao;
	
	@Override
	public Task findTaskById(Long id) {
		
		return taskDao.getById(id);
	}

	@Override
	public void saveTask(Task task) {
		taskDao.save(task);
		
	}

	@Override
	public void updateTask(Task task) {
		taskDao.save(task);
		
	}

	@Override
	public void deleteTask(Long id) {
		taskDao.deleteById(id);
	}

	@Override
	public List<Task> listTasks() {
		
		return taskDao.findAll();
	}

}
