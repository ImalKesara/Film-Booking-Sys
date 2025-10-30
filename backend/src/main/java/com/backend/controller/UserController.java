package com.backend.controller;

import com.backend.model.User;
import com.backend.security.dto.UserDTO;
import com.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.getUserById(id);
        return user.isPresent()
                ? ResponseEntity.ok(user.get())
                : ResponseEntity.status(404).body("User not found");
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted
                ? ResponseEntity.ok("User deleted successfully")
                : ResponseEntity.status(404).body("User not found");
    }

}
