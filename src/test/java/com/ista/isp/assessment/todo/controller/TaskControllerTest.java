package com.ista.isp.assessment.todo.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ista.isp.assessment.todo.interfaces.TaskService;
import com.ista.isp.assessment.todo.model.Task;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
	
	@InjectMocks
	private TaskController taskController = new TaskController();

	@Mock
	private TaskService taskService;
	
	
	@Test
	public void testGetAllTasksSuccess() {
		
		// MOCKS
		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task());
		
		Mockito.when(taskService.getAllTasks()).thenReturn(tasks);
		
		// TEST
		ResponseEntity<List<Task>> result = taskController.getAllTasks();
		
		// VERIFIES
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals(tasks.size(), result.getBody().size());
	}
	
	@Test
	public void testGetAllTasksSuccessReturningEmptyList() {
		
		// MOCKS		
		Mockito.when(taskService.getAllTasks()).thenReturn(new ArrayList<>());
		
		// TEST
		ResponseEntity<List<Task>> result = taskController.getAllTasks();
		
		// VERIFIES
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertEquals(0, result.getBody().size());
	}
	
	@Test
	public void testGetTaskSuccess() {
		
		// MOCKS		
		Mockito.when(taskService.getTask(Mockito.anyLong())).thenReturn(new Task());
		
		// TEST
		ResponseEntity<Task> result = taskController.getTask(1L);
		
		// VERIFIES
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertNotNull(result.getBody());
	}
	
	@Test
	public void testGetTaskErroNotFound() {
		
		// MOCKS		
		Mockito.when(taskService.getTask(Mockito.anyLong())).thenReturn(null);
		
		// TEST
		ResponseEntity<Task> result = taskController.getTask(1L);
		
		// VERIFIES
		Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		Assertions.assertNull(result.getBody());
	}	
	
	@Test
	public void testCreateTaskSuccess() {
		
		// MOCKS		
		Mockito.when(taskService.createTask(Mockito.any(Task.class))).thenReturn(new Task());
		
		// TEST
		ResponseEntity<Task> result = taskController.createTask(new Task());
		
		// VERIFIES
		Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
		Assertions.assertNotNull(result.getBody());
	}
	
	@Test
	public void testUpdateTaskSuccess() {
		
		// MOCKS
		Mockito.when(taskService.getTask(Mockito.anyLong())).thenReturn(new Task());
		
		Mockito.when(taskService.updateTask(Mockito.any(Task.class))).thenReturn(new Task());
		
		Task task = new Task();
		task.setId(1L);
		
		// TEST
		ResponseEntity<Task> result = taskController.updateTask(1L, task);
		
		// VERIFIES
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assertions.assertNotNull(result.getBody());
	}
	
	@Test
	public void testUpdateTaskErrorNotFound() {
		
		// MOCKS		
		Task task = new Task();
		task.setId(1L);
		
		// TEST
		ResponseEntity<Task> result = taskController.updateTask(1L, task);
		
		// VERIFIES
		Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		Assertions.assertNull(result.getBody());
	}
	
	@Test
	public void testUpdateTaskErrorBadRequestMismatchingIds() {
		
		// MOCKS		
		Task task = new Task();
		task.setId(1L);
		
		// TEST
		ResponseEntity<Task> result = taskController.updateTask(2L, task);
		
		// VERIFIES
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		Assertions.assertNull(result.getBody());
	}
	
	@Test
	public void testDeleteTaskSuccess() {
		
		// MOCKS
		Mockito.when(taskService.getTask(Mockito.anyLong())).thenReturn(new Task());

		// TEST
		ResponseEntity<Void> result = taskController.deleteTask(1L);
		
		// VERIFIES
		verify(taskService, times(1)).deleteTask(Mockito.eq(1L));
		Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
		Assertions.assertNull(result.getBody());
	}
	
	@Test
	public void testDeleteTaskErrorNotFound() {
		
		// MOCKS
		Mockito.when(taskService.getTask(Mockito.anyLong())).thenReturn(null);
		
		// TEST
		ResponseEntity<Void> result = taskController.deleteTask(1L);
		
		// VERIFIES
		verify(taskService, times(0)).deleteTask(Mockito.eq(1L));
		Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		Assertions.assertNull(result.getBody());
	}
	
}
