package gym.controller;

import gym.controller.dto.LoginDto;
import gym.entity.Customer;
import gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody Customer user) {
        userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> register(@RequestBody LoginDto dto) {
        try {
            String authenticationResult = userService.authenticateUser(dto.getLogin(), dto.getPassword());

            if (!authenticationResult.equals("not found")) {
                return ResponseEntity.ok(authenticationResult);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
