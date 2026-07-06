package com.settlement.auth.controller;

import com.settlement.auth.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Map<String, String[]> USERS = Map.of(
            "trader1", new String[]{"pass123", "TRADER"},
            "admin", new String[]{"adminpass", "ADMIN"}
    );
    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest req) {
        String[] user = USERS.get(req.username());
        if(user == null || !user[0].equals(req.password()))
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));

        String token = jwtService.generateToken(req.username(), user[1]);
        return ResponseEntity.ok(Map.of("token", token, "role", user[1], "expiresIn", 3600));
    }

    public record LoginRequest(String username, String password) {}
}
