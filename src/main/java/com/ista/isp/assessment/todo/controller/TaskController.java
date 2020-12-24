package com.ista.isp.assessment.todo.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.ista.isp.assessment.todo.interfaces.TaskService;
import com.ista.isp.assessment.todo.model.Task;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TaskController {

	@Autowired
	private TaskService taskService;

	
	@GetMapping("/task")
	public ResponseEntity<List<Task>> getAllTasks() {
	
		List<Task> tasks = taskService.getAllTasks();

		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}
	
	@GetMapping("/task/{id}")
	public ResponseEntity<Task> getTask(@PathVariable Long id) {
		
		ResponseEntity<Task> response;
		
		Task task = taskService.getTask(id);
		
		if (null != task)
			response = new ResponseEntity<Task>(task, HttpStatus.OK);
		else
			response = new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
		
		return response;
	}
	
	@PostMapping("/task")
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
	
		Task creatededTask = taskService.createTask(task);
		
		return new ResponseEntity<Task>(creatededTask, HttpStatus.CREATED);
	}
	
	@PutMapping("/task/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {

		ResponseEntity<Task> response;
		
		if (pathIdMatchBodyId(id, task.getId()))
			
			if (null != taskService.getTask(id))
				response = new ResponseEntity<Task>(taskService.updateTask(task), HttpStatus.OK);
			else
				response = new ResponseEntity<Task>(HttpStatus.NOT_FOUND);
		
		else
			response = new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);
		
		return response;
	}

	@DeleteMapping("/task/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		
		ResponseEntity<Void> response;
		
		Task task = taskService.getTask(id);
		
		if (task == null) {
			response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			taskService.deleteTask(id);
			response = new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		return response;
	}
	
	private boolean pathIdMatchBodyId(Long pathId, Long bodyId) {
		return pathId.equals(bodyId);
	}
}
