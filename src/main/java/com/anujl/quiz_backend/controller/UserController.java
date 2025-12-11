package com.anujl.quiz_backend.controller;

import com.anujl.quiz_backend.dto.login.UserLoginDto;
import com.anujl.quiz_backend.dto.login.UserRegisterDTO;
import com.anujl.quiz_backend.dto.login.UserResponseDTO;
import com.anujl.quiz_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDTO userDTO) {

        boolean created = userService.saveUser(userDTO);

        if (!created) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "User already exists"));
        }

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDto dto) {

        System.out.println("Login attempt for user: " + dto.getUsername());

        String jwt = userService.verify(dto);

        if (jwt == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", jwt
        ));
    }

    // ---------------------------
    // FORGOT PASSWORD
    // ---------------------------
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
//
//        if (!body.containsKey("email") || body.get("email").trim().isEmpty()) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(Map.of("message", "Email is required"));
//        }
//
//        boolean initiated = userService.initiatePasswordReset(body.get("email").trim());
//
//        if (!initiated) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(Map.of("message", "User with given email not found"));
//        }

        return ResponseEntity.ok(Map.of("message", "Password reset initiated"));
    }

    // ---------------------------
    // LOGOUT
    // ---------------------------
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody Map<String, String> body) {

        if (!body.containsKey("token")) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Token is required"));
        }

        userService.logout(body.get("token").trim());

        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }

    // ---------------------------
    // GET ALL USERS
    // ---------------------------
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

        System.out.println("Fetching all users");

        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("UP");
    }

}
