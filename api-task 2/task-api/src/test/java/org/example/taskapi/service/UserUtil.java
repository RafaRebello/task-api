package org.example.taskapi.service;

import org.example.taskapi.domain.Task;
import org.example.taskapi.domain.User;
import org.example.taskapi.domain.enums.TaskStatus;
import org.example.taskapi.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserUtil {


    public static User createUser(){
        return User.builder()
                .id(1L)
                .name("user1")
                .team("Mengo")
                .tasks(new ArrayList<>())
                .build();
    }

    public static UserDTO createUserDTO(){
        return UserDTO.builder()
                .name("user1")
                .team("Mengo")
                .build();
    }


    public static Task createTaskUser1(){
        return Task.builder()
                .id(1L)
                .title("Endpoint1")
                .description("Criar endpoint1")
                .status(TaskStatus.DOING)
                .user(createUser())
                .build();
    }

    public static Task createTaskUser2(){
        return Task.builder()
                .id(2L)
                .title("Endpoint2")
                .description("Criar endpoint2")
                .status(TaskStatus.TODO)
                .user(createUser())
                .build();
    }

    public static List<User> users(){
        List<User> users = new ArrayList<>();

        User user1 = UserUtil.createUser();
        user1.getTasks().add(UserUtil.createTaskUser1());

        User user2 = UserUtil.createUser();
        user2.getTasks().add(UserUtil.createTaskUser2());

        users.add(user1);
        users.add(user2);

        return users;
    }

}
