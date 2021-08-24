package com.netcracker.skillstable.controller;


import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.security.JwtProvider;
import com.netcracker.skillstable.service.dto.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> auth(@RequestBody AuthRequest request) {
        User user = userService.getUserByUsernameAndPassword(request.getUsername(), request.getPassword());
        String jwtToken = jwtProvider.generateToken(user.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwtToken, user));
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class AuthRequest {
    private String username;
    private String password;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class JwtResponse {
    private String jwtToken;
    private User user;
}
