package org.example.taskapi.service;

import org.example.taskapi.domain.Task;
import org.example.taskapi.domain.User;
import org.example.taskapi.domain.enums.TaskStatus;
import org.example.taskapi.dto.TaskDTO;

import java.util.ArrayList;
import java.util.List;

public class TaskUtil {

    public static User createUser(){
        return User.builder()
                .id(1L)
                .name("user1")
                .team("Mengo")
                .tasks(new ArrayList<>())
                .build();
    }

    public static TaskDTO createTaskDTO(){
        return TaskDTO.builder()
                .title("Endpoint")
                .description("Criar endpoint")
                .status(TaskStatus.TODO)
                .userId(1L)
                .build();
    }

    public static Task createTask(){
        return Task.builder()
                .id(1L)
                .title("Endpoint1")
                .description("Criar endpoint1")
                .status(TaskStatus.DOING)
                .user(createUser())
                .build();
    }

    public static Task createTask2(){
        return Task.builder()
                .id(2L)
                .title("Endpoint2")
                .description("Criar endpoint2")
                .status(TaskStatus.TODO)
                .user(createUser())
                .build();
    }

    public static List<Task> tasks(){
        List<Task> tasks = new ArrayList<>();

        Task task1 = TaskUtil.createTask();

        Task task2 = TaskUtil.createTask2();

        tasks.add(task1);
        tasks.add(task2);

        return tasks;
    }
}
