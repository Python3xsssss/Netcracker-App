package com.netcracker.skillstable.web;


import com.netcracker.skillstable.model.ApiResponse;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.security.JwtProvider;
import com.netcracker.skillstable.service.dto.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ApiResponse<String> auth(@RequestBody AuthRequest request) {
        User user = userService.getUserByUsernameAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getUsername());
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Successfully signed in as " + user.getUsername() + ".",
                token
        );
    }
}

@Data
class AuthRequest {
    private String login;
    private String password;
}
