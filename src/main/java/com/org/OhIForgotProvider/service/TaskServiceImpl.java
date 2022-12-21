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
	public Task getTaskById(Long id) {
		Task task = taskDao.getById(id);
		if(task.getId() == id) {
			return task; 
		}else {
			return null; 
		}
		
	}

	@Override
	public Task saveTask(Task task) {
		 return taskDao.save(task);
		
	}

	@Override
	public Task updateTask(Long id, Task task) {
		return taskDao.save(task);
		
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
