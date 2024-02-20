package org.example.taskapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskapi.constants.ErrorMessages;
import org.example.taskapi.domain.Task;
import org.example.taskapi.domain.User;
import org.example.taskapi.domain.enums.ErrorCodeEnum;
import org.example.taskapi.dto.TaskDTO;
import org.example.taskapi.infra.exceptions.TaskException;
import org.example.taskapi.infra.exceptions.UserException;
import org.example.taskapi.repository.TaskRepository;
import org.example.taskapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public Task createTask(TaskDTO taskDTO) {
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new UserException(ErrorCodeEnum.USER_NOT_FOUND,
                        ErrorMessages.NOT_FOUND, "User not found"));
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> findAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            throw new TaskException(ErrorCodeEnum.TASK_LIST_EMPTY, ErrorMessages.BAD_REQUEST, "Task list is empty");
        }
        return tasks;
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new TaskException(ErrorCodeEnum.TASK_NOT_FOUND, ErrorMessages.NOT_FOUND, "Task not found"));

    }

    public void deleteTask(Long id) {
        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() ->  new TaskException(ErrorCodeEnum.TASK_NOT_FOUND, ErrorMessages.NOT_FOUND, "Task not found"));

        taskRepository.delete(foundTask);

    }

    public Task updateTask(Long id, Task task) {
        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskException(
                        ErrorCodeEnum.TASK_NOT_FOUND, ErrorMessages.NOT_FOUND, "Task not found"));
        foundTask.setTitle(task.getTitle());
        foundTask.setDescription(task.getDescription());
        foundTask.setStatus(task.getStatus());
        return taskRepository.save(foundTask);
    }
}
