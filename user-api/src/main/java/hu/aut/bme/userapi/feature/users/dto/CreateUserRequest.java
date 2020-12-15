package hu.aut.bme.userapi.feature.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String username;
    private String email;
}
