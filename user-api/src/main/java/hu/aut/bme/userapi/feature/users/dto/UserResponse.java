package hu.aut.bme.userapi.feature.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private int id;
    private String username;
    private String email;
}

