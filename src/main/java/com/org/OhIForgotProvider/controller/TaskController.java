package com.org.OhIForgotProvider.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.OhIForgotProvider.model.Task;
import com.org.OhIForgotProvider.model.User;
import com.org.OhIForgotProvider.respository.TaskRepository;
import com.org.OhIForgotProvider.respository.UserRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TaskController {

	private final TaskRepository taskDao;
   
	
    public TaskController(TaskRepository taskDao) {
    	this.taskDao = taskDao;
    	
    }
    
    //list of tasks
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> listTasks(){
    	List<Task> tasks = taskDao.findAll();
    	
    	if(tasks.isEmpty()) {
    		return new ResponseEntity<List<Task>>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }
    
    //creates task
    @PostMapping("/tasks")
	public ResponseEntity<Task> saveTask(@RequestBody Task task){
    	
    	User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        task.setUser(user);
        taskDao.save(task);
		return new ResponseEntity<Task>(task, HttpStatus.OK);
	}
    
  //updates task 
  	@PutMapping("/tasks/{id}")
  	public ResponseEntity<Task> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
  		System.out.println("Updating task " + id);

  	  Task currentTask = taskDao.getById(id);

  		if (currentTask == null) {
  			System.out.println("Task with id " + id + " not found");
  			return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
  		}

  		currentTask.setDescription(task.getDescription());
  		currentTask.setDueDate(task.getDueDate());
  		

  		taskDao.save(currentTask);
  		return new ResponseEntity<Task>(currentTask, HttpStatus.OK);
  	}
  	
  	
  	//deletes task 
  	@DeleteMapping("/tasks/{id}")
  	public ResponseEntity<Task> deleteTask(@PathVariable("id") long id) {
          System.out.println("Retrieving & Deleting Task with id " + id);
    
          Task task = taskDao.getById(id);
          if (task == null) {
              System.out.println("Unable to delete. Task with id " + id + " not found");
              return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
          }
    
          taskDao.deleteById(id);
          return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
      }
    
}
