package com.ista.isp.assessment.todo.interfaces;

import java.util.List;

import com.ista.isp.assessment.todo.model.Task;

public interface TaskService {

	List<Task> getAllTasks();
	
	Task getTask(Long taskId);
	
	Task createTask(Task task);

	Task updateTask(Task task);

	void deleteTask(Long taskId);

}
