package hu.aut.bme.userapi.feature.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final List<String> allowedUsers = Arrays.asList("admin1", "admin2", "blogger1");

    @RequestMapping
    public ResponseEntity auth(@Param("username") String username) {
        if (!allowedUsers.contains(username)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
