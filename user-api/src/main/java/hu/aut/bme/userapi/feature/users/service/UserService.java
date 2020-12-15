package hu.aut.bme.userapi.feature.users.service;

import hu.aut.bme.userapi.feature.users.dto.CreateUserRequest;
import hu.aut.bme.userapi.feature.users.dto.UpdateUserRequest;
import hu.aut.bme.userapi.feature.users.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAllUsers();

    UserResponse createUser(CreateUserRequest createUserRequest);

    UserResponse updateUser(int id, UpdateUserRequest updateUserRequest);

    UserResponse findUserById(int id);

    void deleteUserById(int id);
}
