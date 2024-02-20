package org.example.taskapi.service;

import org.example.taskapi.constants.ErrorMessages;
import org.example.taskapi.domain.User;
import org.example.taskapi.domain.enums.ErrorCodeEnum;
import org.example.taskapi.dto.UserDTO;
import org.example.taskapi.infra.exceptions.UserException;
import org.example.taskapi.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class UserServiceTest {




    @Autowired
    private  UserService userService;

    @MockBean
    private UserRepository userRepository;


    @Test
    @DisplayName("Successfully creating user")
    void createUserSuccessfully(){
        //given
        User user = UserUtil.createUser();
        UserDTO userDTO = UserUtil.createUserDTO();

        //when
        when(userRepository.save(any(User.class))).thenReturn(user);

        //then
        User createdUser = userService.create(userDTO);

        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getTeam(), createdUser.getTeam());

    }

    @Test
    @DisplayName("Find all users with success")
    public void testFindAllWithUsers() {
        // given
        List<User> expectedUsers = UserUtil.users();

        // when
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // then
        List<User> foundUsers = userService.findAll();

        assertNotNull(foundUsers);
        assertEquals(expectedUsers, foundUsers);
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Find all users and return with empty list")
    public void testFindAllWhenNoUsers() {
        // given
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        try {
            userService.findAll();
            fail("Should have thrown an exception");
        } catch (UserException e) {
            assertEquals(ErrorCodeEnum.USER_LIST_EMPTY, e.getErrorCodeEnum());
            assertEquals(ErrorMessages.BAD_REQUEST,
                    e.getErrorMessages());
            assertEquals("User list is empty", e.getMessage());
        }
    }

    @Test
    @DisplayName("Find user by id with success")
    public void testFindUserByIdSuccess() {
        // give
        User expectedUser = UserUtil.createUser();
        expectedUser.setId(1L);

        // when
        when(userRepository.findById(1L)).thenReturn(Optional.of(expectedUser));

        // then
        User foundUser = userService.findUserById(1L);

        assertNotNull(foundUser);
        assertEquals(expectedUser, foundUser);
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Find user by id without success")
    public void testFindUserByIdNotFound() {

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        try {
            userService.findUserById(2L);
            fail("Should have thrown an exception");
        } catch (UserException e) {
            assertEquals(ErrorCodeEnum.USER_NOT_FOUND, e.getErrorCodeEnum());
            assertEquals(ErrorMessages.NOT_FOUND, e.getErrorMessages());
            assertEquals("User not found", e.getMessage());
        }
    }

    @Test
    @DisplayName("Delete user by id with success")
    public void testDeleteUserByIdSuccess() {
        // give
        User expectedUser = UserUtil.createUser();
        expectedUser.setId(1L);

        // when
        when(userRepository.findById(1L)).thenReturn(Optional.of(expectedUser));

        // then
        userService.deleteUserById(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(expectedUser);
    }

    @Test
    @DisplayName("Delete user by id without success")
    public void testDeleteUserByIdNotFound() {

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        try {
            userService.deleteUserById(2L);
            fail("Should have thrown an exception");
        } catch (UserException e) {
            assertEquals(ErrorCodeEnum.USER_NOT_FOUND, e.getErrorCodeEnum());
            assertEquals(ErrorMessages.NOT_FOUND, e.getErrorMessages());
            assertEquals("User not found", e.getMessage());
        }

        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    public void testUpdateUserSuccess() {
        // give
        User originalUser = UserUtil.createUser();

        User updatedUser = UserUtil.createUser();
        updatedUser.setTeam("Mengo1981");
        // when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(originalUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(updatedUser.getTeam(), result.getTeam());
    }

    @Test
    @DisplayName("Try update user without success")
    public void testUpdateUserNotFound() {

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        try {
            userService.updateUser(2L, new User());
            fail("Should have thrown an exception");
        } catch (UserException e) {
            assertEquals(ErrorCodeEnum.USER_NOT_FOUND, e.getErrorCodeEnum());
            assertEquals(ErrorMessages.NOT_FOUND, e.getErrorMessages());
            assertEquals("User not found", e.getMessage());
        }

        verify(userRepository, never()).save(any(User.class));
    }

}
