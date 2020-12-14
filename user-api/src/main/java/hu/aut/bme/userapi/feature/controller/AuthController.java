package hu.aut.bme.userapi.feature.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final List<String> allowedUsers = Arrays.asList("admin1", "admin2", "blogger1");

    @RequestMapping(method = {GET, POST})
    public ResponseEntity auth(@RequestHeader("X-Forwarded-User") String user) {
        if (allowedUsers.contains(user)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
