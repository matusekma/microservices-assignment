package hu.aut.bme.userapi.feature.users.controller;

import hu.aut.bme.userapi.feature.users.dto.CreateUserRequest;
import hu.aut.bme.userapi.feature.users.dto.UpdateUserRequest;
import hu.aut.bme.userapi.feature.users.dto.UserResponse;
import hu.aut.bme.userapi.feature.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable int userId) {
        return userService.findUserById(userId);
    }

    @PostMapping
    public UserResponse postUser(@RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable int userId, @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(userId, updateUserRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
    }
}