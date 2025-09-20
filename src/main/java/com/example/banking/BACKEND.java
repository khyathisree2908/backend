package com.example.banking;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Allow frontend at http://localhost:5173 (Vite default) to access this backend
@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/api/auth")
public class BACKEND {

    @Autowired
    private UserRepo userRepo;

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepo.findByUsername(request.getUsername());

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(request.getPassword())) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    // Signup endpoint (optional)
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody LoginRequest request) {
        // Check if username already exists
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(400).body("Username already exists");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());

        userRepo.save(newUser);

        return ResponseEntity.ok(newUser);
    }
}
