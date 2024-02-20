package org.example.taskapi.controller;


import lombok.RequiredArgsConstructor;
import org.example.taskapi.domain.Task;
import org.example.taskapi.dto.TaskDTO;
import org.example.taskapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;


    @GetMapping
    public ResponseEntity<String> getApiStatus() {
        return new ResponseEntity<>("ON", HttpStatus.OK);
    }

    @PostMapping(value = "/tasks/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        Task createdTask = taskService.createTask(taskDTO);

        return ResponseEntity.ok(createdTask);
    }

    @GetMapping(value = "/tasks")
    public ResponseEntity<List<Task>> findAll() {
        List<Task> foundTasks = taskService.findAllTasks();

        return ResponseEntity.ok(foundTasks);
    }

    @GetMapping(value = "/tasks/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        Task foundTask = taskService.findTaskById(id);
        if (isNull(foundTask)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundTask);
        }
    }

    @PutMapping(value = "/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        if (isNull(updatedTask)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedTask);
        }
    }

    @DeleteMapping(value = "/tasks/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
