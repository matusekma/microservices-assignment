package hu.aut.bme.userapi.feature.users.service.impl;

import hu.aut.bme.userapi.domain.User;
import hu.aut.bme.userapi.feature.users.dto.CreateUserRequest;
import hu.aut.bme.userapi.feature.users.dto.UpdateUserRequest;
import hu.aut.bme.userapi.feature.users.dto.UserMappers;
import hu.aut.bme.userapi.feature.users.dto.UserResponse;
import hu.aut.bme.userapi.feature.users.service.UserService;
import hu.aut.bme.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private User findUserByIdOrThrow(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id $id not found."));
    }


    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream().map(UserMappers::userToUserResponse).collect(Collectors.toList());
    }

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        return UserMappers.userToUserResponse(
                userRepository.save(UserMappers.createUserRequestToUser(createUserRequest))
        );
    }

    public UserResponse updateUser(int id, UpdateUserRequest updateUserRequest) {
        User user = findUserByIdOrThrow(id);
        user.setUsername(updateUserRequest.getUsername());
        return UserMappers.userToUserResponse(userRepository.save(user));
    }

    public UserResponse findUserById(int id) {
        return UserMappers.userToUserResponse(findUserByIdOrThrow(id));
    }


    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }
}