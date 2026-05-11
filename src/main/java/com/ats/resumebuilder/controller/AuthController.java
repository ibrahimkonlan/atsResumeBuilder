package com.ats.resumebuilder.controller;

import com.ats.resumebuilder.model.AppUser;
import com.ats.resumebuilder.model.AuthResponse;
import com.ats.resumebuilder.model.LoginRequest;
import com.ats.resumebuilder.model.RegisterRequest;
import com.ats.resumebuilder.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body(new AuthResponse(false, "Email is required."));
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body(new AuthResponse(false, "Password must be at least 6 characters."));
        }
        if (appUserRepository.existsByEmail(request.getEmail().toLowerCase())) {
            return ResponseEntity.badRequest().body(new AuthResponse(false, "An account with this email already exists."));
        }

        AppUser newUser = new AppUser(
            request.getFullName(),
            request.getEmail().toLowerCase(),
            request.getPassword() // In production, hash with BCrypt
        );
        AppUser saved = appUserRepository.save(newUser);

        return ResponseEntity.ok(new AuthResponse(
            true,
            "Account created successfully! Welcome, " + saved.getFullName() + ".",
            saved.getId(),
            saved.getFullName(),
            saved.getEmail()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        if (request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(new AuthResponse(false, "Email and password are required."));
        }

        Optional<AppUser> userOpt = appUserRepository.findByEmail(request.getEmail().toLowerCase());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(new AuthResponse(false, "No account found with this email."));
        }

        AppUser user = userOpt.get();
        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body(new AuthResponse(false, "Incorrect password. Please try again."));
        }

        return ResponseEntity.ok(new AuthResponse(
            true,
            "Login successful. Welcome back, " + user.getFullName() + "!",
            user.getId(),
            user.getFullName(),
            user.getEmail()
        ));
    }
}
