package com.caspercodes.todolist.controller;

import com.caspercodes.todolist.security.CurrentUser;
import com.caspercodes.todolist.dto.PagedTaskResponse;
import com.caspercodes.todolist.dto.TaskRequest;
import com.caspercodes.todolist.dto.TaskResponse;
import com.caspercodes.todolist.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final CurrentUser currentUser;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        TaskResponse taskResponse = taskService.createTask(request, currentUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable UUID taskId,
            @Valid @RequestBody TaskRequest request) {
        TaskResponse taskResponse = taskService.updateTask(taskId, request, currentUser.getEmail());
        return ResponseEntity.ok(taskResponse);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId, currentUser.getEmail());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable UUID taskId) {
        TaskResponse taskResponse = taskService.getTask(taskId, currentUser.getEmail());
        return ResponseEntity.ok(taskResponse);
    }

    @GetMapping
    public ResponseEntity<PagedTaskResponse> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        PagedTaskResponse response = taskService.getTasks(currentUser.getEmail(), page, limit);
        return ResponseEntity.ok(response);
    }
}