package com.tianchen.homehub_backend.Controller;

import com.tianchen.homehub_backend.Service.UserService;
import com.tianchen.homehub_backend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String usernameOrEmail, @RequestParam String password){
       String loginResponse = userService.loginUser(usernameOrEmail, password);
       if (loginResponse.equals("Login successful")){
           return ResponseEntity.ok("Login successful");
       } else {
           return ResponseEntity.badRequest().body("Invalid credentials"); //status code 401 if login fails
       }
    }
}
