package com.tianchen.homehub_backend.Service;

import com.tianchen.homehub_backend.model.User;
import com.tianchen.homehub_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // BCryptPasswordEncoder is used to hash passwords
    }

    @Transactional
    public String registerUser(User user){
        if(userRepository.findByUsername(user.username()).isPresent()){
            return "Username already exists";
        }
        if(userRepository.findByEmail(user.email()).isPresent()){
            return "Email already exists";
        }

        String  hashedPassword = passwordEncoder.encode(user.password());
        User userwithHashedPassword = new User(user.id(), user.communityName(), user.username(), hashedPassword, user.email(), user.userType());
        userRepository.saveUser(userwithHashedPassword);
        return "User registered successfully";
    }

    public String loginUser(String usernameOrEmail, String password) {
        // Try to find the user by username
        Optional<User> userOpt = userRepository.findByUsername(usernameOrEmail);
        if (userOpt.isEmpty()) {
            // If no user is found by username, try to find by email
            userOpt = userRepository.findByEmail(usernameOrEmail);
            if (userOpt.isEmpty()) {
                return "User not found";
            }
        }

        User user = userOpt.get();
        // Compare the raw password with the hashed password
        if (passwordEncoder.matches(password, user.password())) {
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }

    public User findByUsernameOrEmail(String usernameOrEmail) {
        Optional<User> userOpt = userRepository.findByUsername(usernameOrEmail);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(usernameOrEmail);
        }
        return userOpt.orElse(null);
    }

}