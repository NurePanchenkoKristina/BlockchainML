package gym.service;

import gym.entity.Customer;
import gym.repository.CustomerRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CustomerRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void registerUser(Customer user) {
        if (userRepository.findByLogin(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        user.setJoinDate(LocalDate.now());
        user.setRole("user");
        user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));

        userRepository.save(user);
    }

    public String authenticateUser(String username, String password) {
        Customer user = userRepository.findByLogin(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getHashedPassword())) {
            return "not found";
        } else return user.getRole();
    }
}
