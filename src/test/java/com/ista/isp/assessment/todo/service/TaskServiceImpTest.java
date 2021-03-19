package com.ista.isp.assessment.todo.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.ista.isp.assessment.todo.model.Task;
import com.ista.isp.assessment.todo.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImpTest {
	
	@InjectMocks
	private TaskServiceImp taskServiceImp = new TaskServiceImp();

	@Mock
	private TaskRepository taskRepository;
	
	@Test
	public void testGetAllTasksSuccess() {
		
		// MOCKS
		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task());
		
		Mockito.when(taskRepository.findAll()).thenReturn(tasks);
		
		// TEST
		List<Task> result = taskServiceImp.getAllTasks();
		
		// VERIFIES
		Assertions.assertEquals(1, result.size());
	}
	
	@Test
	public void testGetTaskSuccess() {
		
		// MOCKS
		Mockito.when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Task()));
		
		// TEST
		Task result = taskServiceImp.getTask(1L);
		
		// VERIFIES
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void testGetTaskSuccessReturningNull() {
		
		// MOCKS
		Mockito.when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		// TEST
		Task result = taskServiceImp.getTask(1L);
		
		// VERIFIES
		Assertions.assertNull(result);
	}
		
	@Test
	public void testUpdateTaskSuccess() {
		
		// MOCKS
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(new Answer<Task>() {
			@Override
			public Task answer(InvocationOnMock invocationOnMock) throws Throwable {
				return (Task) invocationOnMock.getArguments()[0];
			}
		});
		
		Task task = new Task();
		task.setId(1L);
		task.setText("Test text");
		task.setChecked(Boolean.FALSE);
		
		// TEST
		Task result = taskServiceImp.updateTask(task);
		
		// VERIFIES
		Assertions.assertEquals(task, result);
	}
	
	@Test
	public void testCreateTaskSuccess() {
		
		// MOCKS
		Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(new Answer<Task>() {
			@Override
			public Task answer(InvocationOnMock invocationOnMock) throws Throwable {
				Task task = (Task) invocationOnMock.getArguments()[0];
				task.setId(1L);
				return task;
			}
		});
		
		Task task = new Task();
		task.setText("Test text");
		task.setChecked(Boolean.FALSE);
		
		// TEST
		Task result = taskServiceImp.createTask(task);
		
		// VERIFIES
		Assertions.assertEquals(task.getText(), result.getText());
		Assertions.assertEquals(task.getChecked(), result.getChecked());
		Assertions.assertNotNull(result.getId());
	}
	
	@Test
	public void testDeleteTaskSuccess() {

		// TEST
		taskServiceImp.deleteTask(1L);
		
		// VERIFIES
		verify(taskRepository, times(1)).deleteById(Mockito.eq(1L));
	}
}
