package com.ista.isp.assessment.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ista.isp.assessment.todo.interfaces.TaskService;
import com.ista.isp.assessment.todo.model.Task;
import com.ista.isp.assessment.todo.repository.TaskRepository;

@Service
public class TaskServiceImp implements TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public List<Task> getAllTasks() {
		
		List<Task> tasks = taskRepository.findAll();
		
		return tasks;
	}
	
	@Override
	public Task getTask(Long taskId) {
		
		Task task = taskRepository.findById(taskId).orElse(null);
		
		return task;
	}
	
	
	@Override
	public Task updateTask(Task task) {
		
		Task updatedTask = taskRepository.save(task);
		
		return updatedTask;
	}
	
	
	@Override
	public Task createTask(Task task) {
		
		Task savedTask = taskRepository.save(task);
		
		return savedTask;
	}
	
	@Override
	public void deleteTask(Long taskId) {
		
		taskRepository.deleteById(taskId);
	}
	
}
