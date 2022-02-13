package pl.nullpointerexception.minikurstesty.service;

import org.springframework.stereotype.Service;
import pl.nullpointerexception.minikurstesty.model.User;
import pl.nullpointerexception.minikurstesty.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User nie istnieje"));
    }
}
