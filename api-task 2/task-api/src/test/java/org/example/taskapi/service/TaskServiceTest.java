package org.example.taskapi.service;

import org.example.taskapi.constants.ErrorMessages;
import org.example.taskapi.domain.Task;
import org.example.taskapi.domain.User;
import org.example.taskapi.domain.enums.ErrorCodeEnum;
import org.example.taskapi.domain.enums.TaskStatus;
import org.example.taskapi.dto.TaskDTO;
import org.example.taskapi.infra.exceptions.TaskException;
import org.example.taskapi.infra.exceptions.UserException;
import org.example.taskapi.repository.TaskRepository;
import org.example.taskapi.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Create task with success")
    public void testCreateTaskSuccess() {
        // give

        TaskDTO taskDTO = TaskUtil.createTaskDTO();
        User expectedUser = TaskUtil.createUser();

        Task expectedTask = TaskUtil.createTask();

        // when
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        when(taskRepository.save(any(Task.class))).thenReturn(expectedTask);

        // then
        Task createdTask = taskService.createTask(taskDTO);

        assertNotNull(createdTask);
        assertEquals(expectedTask.getTitle(), createdTask.getTitle());
        assertEquals(expectedTask.getDescription(), createdTask.getDescription());
        assertEquals(expectedTask.getStatus(), createdTask.getStatus());
        assertEquals(expectedTask.getUser().getName(), createdTask.getUser().getName());
        assertEquals(expectedTask.getUser().getTeam(), createdTask.getUser().getTeam());

    }

    @Test
    @DisplayName("Try create task but user is not found")
    public void testCreateTaskUserNotFound() {
        // give
        TaskDTO taskDTO = TaskUtil.createTaskDTO();
        User expectedUser = TaskUtil.createUser();

        // when
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.empty());

        // then
        try {
            taskService.createTask(taskDTO);
            fail("Should have thrown an exception");
        } catch (UserException e) {
            assertEquals(ErrorCodeEnum.USER_NOT_FOUND, e.getErrorCodeEnum());
            assertEquals(ErrorMessages.NOT_FOUND, e.getErrorMessages());
            assertEquals("User not found", e.getMessage());
        }

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Find all tasks with success")
    public void testFindAllTasksSuccess() {
        // give
        List<Task> expectedTasks = TaskUtil.tasks();

        // when
        when(taskRepository.findAll()).thenReturn(expectedTasks);

        // then
        List<Task> foundTasks = taskService.findAllTasks();

        assertNotNull(foundTasks);
        assertEquals(expectedTasks, foundTasks);
        verify(taskRepository).findAll();
    }

    @Test
    @DisplayName("Find all tasks without success")
    public void testFindAllTasksWhenNoTasks() {

        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        try {
            taskService.findAllTasks();
            fail("Should have thrown an exception");
        } catch (TaskException e) {
            assertEquals(ErrorCodeEnum.TASK_LIST_EMPTY, e.getErrorCodeEnum());
            assertEquals(ErrorMessages.BAD_REQUEST, e.getErrorMessages());
            assertEquals("Task list is empty", e.getMessage());
        }
    }

    @Test
    @DisplayName("Find all tasks by id with success")
    public void testFindTaskByIdSuccess() {
        // give
        Task expectedTask = TaskUtil.createTask();

        // when
        when(taskRepository.findById(1L)).thenReturn(Optional.of(expectedTask));

        // then
        Task foundTask = taskService.findTaskById(1L);

        assertNotNull(foundTask);
        assertEquals(expectedTask, foundTask);
        verify(taskRepository).findById(1L);
    }

    @Test@DisplayName("Try find all tasks by id without success")
    public void testFindTaskByIdNotFound() {

        when(taskRepository.findById(2L)).thenReturn(Optional.empty());

        try {
            taskService.findTaskById(2L);
            fail("Should have thrown an exception");
        } catch (TaskException e) {
            assertEquals(ErrorCodeEnum.TASK_NOT_FOUND, e.getErrorCodeEnum());
            assertEquals(ErrorMessages.NOT_FOUND, e.getErrorMessages());
            assertEquals("Task not found", e.getMessage());
        }
    }

    @Test
    @DisplayName("Delete task with success")
    public void testDeleteTaskSuccess() {
        // give
        Task expectedTask = TaskUtil.createTask();

        // when
        when(taskRepository.findById(1L)).thenReturn(Optional.of(expectedTask));

        // then
        taskService.deleteTask(1L);

        verify(taskRepository).findById(1L);
        verify(taskRepository).delete(expectedTask);
    }

    @Test
    @DisplayName("Delete task without success")
    public void testDeleteTaskNotFound() {

        when(taskRepository.findById(2L)).thenReturn(Optional.empty());

        try {
            taskService.deleteTask(2L);
            fail("Should have thrown an exception");
        } catch (TaskException e) {
            assertEquals(ErrorCodeEnum.TASK_NOT_FOUND, e.getErrorCodeEnum());
            assertEquals(ErrorMessages.NOT_FOUND, e.getErrorMessages());
            assertEquals("Task not found", e.getMessage());
        }

        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    public void testUpdateTaskSuccess() {
        // give
        Task originalTask = TaskUtil.createTask();

        Task updatedTask = TaskUtil.createTask();
        updatedTask.setStatus(TaskStatus.DONE);

        // when
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(originalTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // then
        Task result = taskService.updateTask(1L, updatedTask);

        assertNotNull(result);
        assertEquals(updatedTask.getTitle(), result.getTitle());
        assertEquals(updatedTask.getDescription(), result.getDescription());
        assertEquals(updatedTask.getStatus(), result.getStatus());
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(updatedTask);
    }

    @Test
    @DisplayName("Try update task without success")
    public void testUpdateTaskNotFound() {

        when(taskRepository.findById(2L)).thenReturn(Optional.empty());

        try {
            taskService.updateTask(2L, new Task());
            fail("Should have thrown an exception");
        } catch (TaskException e) {
            assertEquals(ErrorCodeEnum.TASK_NOT_FOUND, e.getErrorCodeEnum());
            assertEquals(ErrorMessages.NOT_FOUND, e.getErrorMessages());
            assertEquals("Task not found", e.getMessage());
        }

        verify(taskRepository, never()).save(any(Task.class));
    }
}
