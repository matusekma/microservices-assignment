package hu.aut.bme.userapi.feature.users.dto;

import hu.aut.bme.userapi.domain.User;

public class UserMappers {
    public static UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .id(user.getId())
                .build();
    }

    public static User createUserRequestToUser(CreateUserRequest createUserRequest) {
        return User.builder()
                .username(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .build();
    }
}



