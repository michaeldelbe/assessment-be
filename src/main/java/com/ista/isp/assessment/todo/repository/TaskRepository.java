package com.ista.isp.assessment.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ista.isp.assessment.todo.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
	
	@Query("SELECT t "
			+ "FROM Task t "
			+ "ORDER BY t.checked ASC")
	List<Task> findAll();
	
}
