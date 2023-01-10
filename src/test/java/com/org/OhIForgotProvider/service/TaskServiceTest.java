package com.org.OhIForgotProvider.service;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;

import com.org.OhIForgotProvider.model.Task;
import com.org.OhIForgotProvider.respository.TaskRepository;

@Test
public class TaskServiceTest extends AbstractTestNGSpringContextTests{
	 
	@Mock
	TaskRepository mockDao; 
	
	@InjectMocks
	TaskServiceImpl taskService; 
	
	@BeforeMethod
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void whenListIsCalled_ShowsListOfTasks() {
		Task task1 = new Task();
		task1.setDescription("Get groceries");
		task1.setDueDate(Date.valueOf(LocalDate.now()));
		
		Task task2 = new Task();
		task2.setDescription("Go for run");
		
		Task task3 = new Task();
		task3.setDescription("study for exam");
		task3.setDueDate(Date.valueOf(LocalDate.of(2022, 12, 23)));
		
		List<Task> tasks = new ArrayList<>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
		
		when(mockDao.findAll()).thenReturn(tasks);
		
		List<Task> mockTasks = taskService.listTasks();
		
		assertEquals(mockTasks, tasks);
		
		verify(mockDao, times(1)).findAll();
		
	}
	
	@Test 
	public void whenRetrievingList_ShowsNoTask() {
		List<Task> emptyTasks = new ArrayList<>();
		
		when(mockDao.findAll()).thenReturn(emptyTasks);
		
		List<Task> tasks = taskService.listTasks();
		
		assertEquals(tasks, emptyTasks);
		
		verify(mockDao, atMost(2)).findAll();
	}
	
	@Test
	public void whenGivenId_returnsTask() {
		Task task1 = new Task();
		task1.setId(1L);
		task1.setDescription("Get groceries");
		task1.setDueDate(Date.valueOf(LocalDate.now()));
		
		Task task2 = new Task();
		task2.setId(2L);
		task2.setDescription("Go for run");
		
		Task task3 = new Task();
		task3.setId(3L);
		task3.setDescription("study for exam");
		task3.setDueDate(Date.valueOf(LocalDate.of(2022, 12, 23)));
		
		
		when(mockDao.getById(2L)).thenReturn(task2);
		
		assertEquals(taskService.getTaskById(2L), task2);
		
		verify(mockDao).getById(2L);
		
	}
	
	
	@Test
	public void whenGivenId_ReturnsNull() {
		Task task1 = new Task();
		task1.setId(1L);
		task1.setDescription("Get groceries");
		task1.setDueDate(Date.valueOf(LocalDate.now()));
		
		Task task2 = new Task();
		task2.setId(2L);
		task2.setDescription("Go for run");
		
		when(mockDao.getById(3L)).thenThrow(new NullPointerException());
		
		assertThrows(NullPointerException.class, () -> taskService.getTaskById(3L));
		
		
	}
	
	
	@Test
	public void whenSaveTask_returnTask() {
		Task task1 = new Task();
		task1.setId(1L);
		task1.setDescription("Get groceries");
		task1.setDueDate(Date.valueOf(LocalDate.now()));
		
		when(mockDao.save(Mockito.any())).thenReturn(task1);
		
		Task savedTask = taskService.saveTask(task1);
		
		assertEquals(savedTask, task1);
		
		verify(mockDao).save(task1);
	}
	
	@Test
	public void descriptionEmpty_CannotSaveTask() {
		Task task1 = new Task();
		task1.setId(1L);
		task1.setDueDate(Date.valueOf(LocalDate.now()));
		
		when(mockDao.save(Mockito.any())).thenThrow(new NullPointerException());
		
		
		assertThrows(NullPointerException.class, () -> taskService.saveTask(task1));
		
	}
	
	@Test
	public void whenUpdatingTask_ReturnsUpdatedTask() {
		Task task = new Task();
		task.setId(1L);
		task.setDescription("Get groceries");
		task.setDueDate(Date.valueOf(LocalDate.now()));
		
		Task updatedTask = new Task();
		updatedTask.setId(1L);
		updatedTask.setDescription("Get groceries and pick up clothes.");
		updatedTask.setDueDate(Date.valueOf(LocalDate.of(2023, 01, 02)));
		
		when(mockDao.save(Mockito.any())).thenReturn(task, updatedTask);
		when(mockDao.getById(Mockito.anyLong())).thenReturn(task);
		
		Task savedTask = taskService.saveTask(task);
		savedTask.setDescription(updatedTask.getDescription());
		savedTask.setDueDate(updatedTask.getDueDate());
		
		Task savedTaskUpdate = taskService.updateTask(1L, savedTask);
		
		System.out.println(savedTaskUpdate.getDescription());
		assertNotNull(savedTaskUpdate);
		assertEquals(savedTaskUpdate.getDescription(), "Get groceries and pick up clothes.");
		
	}
	
	@Test
	public void whenUpdating_DescriptionIsBlank_ReturnsNull() {
		Task task = new Task();
		task.setId(1L);
		task.setDescription("Get groceries");
		task.setDueDate(Date.valueOf(LocalDate.now()));
		
		Task updatedTask = new Task();
		updatedTask.setId(1L);
		updatedTask.setDescription("");
		updatedTask.setDueDate(Date.valueOf(LocalDate.of(2023, 01, 02)));
		
		when(mockDao.save(Mockito.any())).thenThrow(new NullPointerException());
		when(mockDao.getById(Mockito.anyLong())).thenReturn(task);
		
		Task savedTask = new Task();
		savedTask.setDueDate(updatedTask.getDueDate());
		
		
		assertThrows(NullPointerException.class, () -> taskService.updateTask(1L, savedTask));
		
	}
	
	
	 
	@Test
	public void whenGivenId_DeleteTask(){
		Task task = new Task();
		task.setId(1L);
		task.setDescription("Get groceries");
		task.setDueDate(Date.valueOf(LocalDate.now()));
		
		taskService.saveTask(task);
		
		List<Task> list = taskService.listTasks();
		
		when(mockDao.getById(task.getId())).thenReturn(task);
		
		
		taskService.deleteTask(task.getId());
		
		assertTrue(list.isEmpty());
		
		verify(mockDao).deleteById(task.getId());
		}

}




