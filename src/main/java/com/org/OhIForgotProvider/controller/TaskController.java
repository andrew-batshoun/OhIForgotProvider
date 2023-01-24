package com.org.OhIForgotProvider.controller;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.org.OhIForgotProvider.service.TaskService;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@RestController
public class TaskController {
 
	@Autowired
	private TaskService taskService;

	// list of tasks
	@GetMapping("/tasks")
	public ResponseEntity<List<Task>> listTasks() {
		List<Task> tasks = taskService.listTasks();

		if (tasks.isEmpty()) {
			return new ResponseEntity<List<Task>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}

	@GetMapping("/tasks/{id}")
	public ResponseEntity<Task> getTask(@PathVariable("id") Long id) {
		
		Task getTask = taskService.getTaskById(id);
		
		if (getTask == null) {
			System.out.println("Task with id: " + id + " does not exist");
			return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);

		}
		
		return new ResponseEntity<Task>(getTask, HttpStatus.OK);
	}

	// creates task
	@PostMapping("/tasks")
	public ResponseEntity<Task> saveTask(@RequestBody Task task) {

		
		taskService.saveTask(task);
		return new ResponseEntity<Task>(task, HttpStatus.OK);
	}

	// updates task
	@SuppressWarnings("deprecation")
	@PutMapping("/tasks/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
		System.out.println("Updating task " + id);

		Task currentTask = taskService.getTaskById(id);
		
		if (currentTask == null) {
			System.out.println("Task with id " + id + " not found");
			return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
		}
		
		
		currentTask.setDescription(task.getDescription());
		currentTask.setDueDate(task.getDueDate());

		taskService.updateTask(id, currentTask);
		return new ResponseEntity<Task>(currentTask, HttpStatus.OK);
	}

	// deletes task
	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<Task> deleteTask(@PathVariable("id") long id) {
		System.out.println("Retrieving & Deleting Task with id " + id);

		Task task = taskService.getTaskById(id);
		if (task == null) {
			System.out.println("Unable to delete. Task with id " + id + " not found");
			return new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
		}

		taskService.deleteTask(id);
		return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
	}

}
