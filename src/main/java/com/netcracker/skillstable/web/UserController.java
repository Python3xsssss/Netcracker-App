package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.ApiResponse;
import com.netcracker.skillstable.model.dto.SkillLevel;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ApiResponse<User> saveUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "User saved successfully.",
                userService.createUser(user)
        );
    }

    @PostMapping("/{id}/skillLevels")
    public ApiResponse<SkillLevel> saveSkillLevel(
            @RequestBody SkillLevel skillLevel,
            @PathVariable(value = "id") Integer userId
    ) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Skill Level saved successfully.",
                userService.createOrUpdateSkillLevel(userId, skillLevel)
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
    public ApiResponse<User> getUser(@PathVariable(value = "id") Integer userId) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "User fetched successfully.",
                userService.getUserById(userId)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable(value = "id") Integer userId, @RequestBody User user){
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "User (id: " + userId + ") updated successfully.",
                userService.updateUser(user)
        );
    }

    @PutMapping("/{userId}/skillLevels/{levelId}")
    public ApiResponse<SkillLevel> updateSkillLevel(
            @RequestBody SkillLevel skillLevel,
            @PathVariable(value = "userId") Integer userId,
            @PathVariable(value = "levelId") Integer skillLevelId
    ) {
        skillLevel.setId(skillLevelId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Skill Level (id: " + skillLevelId + ") of user (id: " + userId + ") updated successfully.",
                userService.createOrUpdateSkillLevel(userId, skillLevel)
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

    @DeleteMapping("/{userId}/skillLevels/{levelId}")
    public ApiResponse<Void> deleteSkillLevel(
            @PathVariable(value = "userId") Integer userId,
            @PathVariable(value = "levelId") Integer skillLevelId
    ) {
        userService.deleteSkillLevel(userId, skillLevelId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Skill Level of user (id: " + userId + ") deleted successfully.",
                null
        );
    }
}
