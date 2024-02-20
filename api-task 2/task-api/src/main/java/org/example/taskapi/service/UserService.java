package org.example.taskapi.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskapi.constants.ErrorMessages;
import org.example.taskapi.domain.User;
import org.example.taskapi.domain.enums.ErrorCodeEnum;
import org.example.taskapi.dto.UserDTO;
import org.example.taskapi.infra.exceptions.UserException;
import org.example.taskapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User create(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setTeam(userDTO.getTeam());

        return userRepository.save(user);
    }

    public List<User> findAll() {
        List<User> user = userRepository.findAll();
        if (user.isEmpty()) {
            throw new UserException(ErrorCodeEnum.USER_LIST_EMPTY, ErrorMessages.BAD_REQUEST, "User list is empty");
        }
        return user;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
        .orElseThrow(()
                -> new UserException(ErrorCodeEnum.USER_NOT_FOUND, ErrorMessages.NOT_FOUND, "User not found"));
    }

    public void deleteUserById(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException(
                        ErrorCodeEnum.USER_NOT_FOUND, ErrorMessages.NOT_FOUND, "User not found"));
        userRepository.delete(foundUser);
    }

    public User updateUser(Long id, User user) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException(
                        ErrorCodeEnum.USER_NOT_FOUND, ErrorMessages.NOT_FOUND, "User not found"));
        foundUser.setName(user.getName());
        foundUser.setTeam(user.getTeam());
        foundUser.setTasks(user.getTasks());
        return userRepository.save(foundUser);
    }
}
