package com.org.OhIForgotProvider.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.OhIForgotProvider.dto.TaskDTO;
import com.org.OhIForgotProvider.model.Task;
import com.org.OhIForgotProvider.model.User;
import com.org.OhIForgotProvider.service.TaskService;



@RequestMapping("/api")
@RestController
public class TaskController {
 
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ModelMapper modelMapper;

	// list of tasks by user
	@GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<TaskDTO> listTasks() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Task> tasks = taskService.findAllByUserId(user.getId());
		
		return tasks.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	
	}

	@GetMapping("/tasks/{id}")
	public ResponseEntity<TaskDTO> getTask(@PathVariable("id") Long id) {
		
		TaskDTO getTask = convertToDto(taskService.getTaskById(id));
		
		return new ResponseEntity<>(getTask, HttpStatus.OK);
	}

	// creates task
	@PostMapping("/tasks")
	public ResponseEntity<TaskDTO> saveTask(@RequestBody TaskDTO task) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Task createTask = convertToEntity(task);
		createTask.setUser(user);
		Task saved = taskService.saveTask(createTask);
		return new ResponseEntity<>(convertToDto(saved), HttpStatus.OK);
	}

	// updates task
	@PutMapping("/tasks/{id}")
	public ResponseEntity<TaskDTO> updateTask(@PathVariable("id") long id, @RequestBody TaskDTO task) {
		
		Task update = convertToEntity(task);
		Task updatedTask = taskService.updateTask(id, update);
		return new ResponseEntity<>(convertToDto(updatedTask), HttpStatus.OK);
	}

	// deletes task
	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable("id") long id) {
		
		
		taskService.deleteTask(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	private Task convertToEntity(TaskDTO taskDto) {
		return modelMapper.map(taskDto, Task.class);
	}
	
	private TaskDTO convertToDto(Task task) {
		return modelMapper.map(task, TaskDTO.class);
	}

}
