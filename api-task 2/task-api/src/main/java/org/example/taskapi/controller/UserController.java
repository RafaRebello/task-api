package org.example.taskapi.controller;


import lombok.RequiredArgsConstructor;
import org.example.taskapi.domain.User;
import org.example.taskapi.dto.UserDTO;
import org.example.taskapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/create")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        User createdUser = userService.create(userDTO);

        return ResponseEntity.ok(createdUser);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> findAll() {
        List<User> foundUser = userService.findAll();

        return ResponseEntity.ok(foundUser);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id){
        User foundUser = userService.findUserById(id);
        if (isNull(foundUser)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundUser);
        }
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User updatedUser = userService.updateUser(id, user);
        if (isNull(updatedUser)){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(updatedUser);
        }
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }
}
