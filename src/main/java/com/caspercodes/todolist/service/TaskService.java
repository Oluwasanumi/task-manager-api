package com.caspercodes.todolist.service;

import com.caspercodes.todolist.dto.PagedTaskResponse;
import com.caspercodes.todolist.dto.TaskRequest;
import com.caspercodes.todolist.dto.TaskResponse;
import com.caspercodes.todolist.model.Task;
import com.caspercodes.todolist.model.TaskRepository;
import com.caspercodes.todolist.model.User;
import com.caspercodes.todolist.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(TaskRequest request, String userEmail) {
        User user = getUserByEmail(userEmail);

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return mapToTaskResponse(savedTask);
    }

    public TaskResponse updateTask(UUID taskId, TaskRequest request, String userEmail) {
        User user = getUserByEmail(userEmail);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));


        if (!task.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Forbidden");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        Task updatedTask = taskRepository.save(task);

        return mapToTaskResponse(updatedTask);
    }

    public void deleteTask(UUID taskId, String userEmail) {
        User user = getUserByEmail(userEmail);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));


        if (!task.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Forbidden");
        }

        taskRepository.delete(task);
    }

    public PagedTaskResponse getTasks(String userEmail, int page, int limit) {
        User user = getUserByEmail(userEmail);

        Pageable pageable = PageRequest.of(page, limit);
        Page<Task> taskPage = taskRepository.findByUser(user, pageable);


        Page<TaskResponse> taskResponsePage = taskPage.map(this::mapToTaskResponse);

        return new PagedTaskResponse(
                taskResponsePage.getContent(),
                page,
                limit,
                taskPage.getTotalElements()
        );
    }

    public TaskResponse getTask(UUID taskId, String userEmail) {
        User user = getUserByEmail(userEmail);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));


        if (!task.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Forbidden");
        }

        return mapToTaskResponse(task);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private TaskResponse mapToTaskResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription());
    }
}