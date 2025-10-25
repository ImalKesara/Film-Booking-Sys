package com.backend.controller;

import com.backend.model.User;
import com.backend.model.UserRole;
import com.backend.repository.UserRepository;
import com.backend.security.dto.AuthRequest;
import com.backend.security.jwt.JwtService;
import com.backend.service.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getUserRole() == null) {
            user.setUserRole(UserRole.USER); // Set default role
        }
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        var user = userRepository.findByEmail(loginRequest.getUsername())
                .orElseThrow();

        String token = jwtService.generateToken(user);
        return Map.of("token", token);
    }
    @GetMapping("/me")
    public ResponseEntity<Map<String,Object>> getCurrentUser(Authentication authentication){
        try {
         if(authentication == null || !authentication.isAuthenticated()){
             return ResponseEntity.status(401).body(Map.of("message","User is not authenticated"));
         }

         String email = authentication.getName();

         //find user by email
         User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));

         Map<String,Object> response = new HashMap<>();

         response.put("id",user.getUserId());
         response.put("name",user.getName());
         response.put("email",user.getEmail());
         response.put("role",user.getRole());

         return ResponseEntity.ok(response);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}
