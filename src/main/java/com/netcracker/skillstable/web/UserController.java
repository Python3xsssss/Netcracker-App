package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.ApiResponse;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse<User> saveUser(@RequestBody User user) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "User saved successfully.",
                userService.createOrUpdateUser(user)
        );
    }

    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "User list fetched successfully.",
                userService.getAllUsers()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<Optional<User>> getUser(@PathVariable(value = "id") Integer userId) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "User fetched successfully.",
                userService.getUserById(userId)
        );
    }

    @PutMapping
    public ApiResponse<User> updateUser(@RequestBody User user){
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "User updated successfully.",
                userService.createOrUpdateUser(user)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable(value = "id") Integer userId) {
        userService.deleteUser(userId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "User deleted successfully.",
                null
        );
    }
}
