package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.ApiResponse;
import com.netcracker.skillstable.model.dto.SkillLevel;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.model.dto.attr.Role;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PreUpdate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @PreAuthorize("hasAuthority('user:create')")
    public ApiResponse<User> saveUser(@RequestBody User user) {
        return new ApiResponse<>(
                HttpStatus.OK,
                "User saved successfully.",
                userService.createUser(user)
        );
    }

    @PostMapping("/{id}/skillLevels")
    @PreAuthorize("hasAuthority('user:update') or #userId == authentication.principal.id")
    public ApiResponse<SkillLevel> saveSkillLevel(
            @RequestBody SkillLevel skillLevel,
            @PathVariable(value = "id") Integer userId
    ) {
        return new ApiResponse<>(
                HttpStatus.OK,
                "Skill Level saved successfully.",
                userService.createOrUpdateSkillLevel(userId, skillLevel)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CREATOR')")
    public ApiResponse<List<User>> getAllUsers() {
        return new ApiResponse<>(
                HttpStatus.OK,
                "User list fetched successfully.",
                userService.getAllUsers()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ApiResponse<User> getUser(@PathVariable(value = "id") Integer userId) {
        return new ApiResponse<>(
                HttpStatus.OK,
                "User fetched successfully.",
                userService.getUserById(userId)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:update') or #userId == authentication.principal.id")
    public ApiResponse<User> updateUser(@PathVariable(value = "id") Integer userId, @RequestBody User user){
        return new ApiResponse<>(
                HttpStatus.OK,
                "User (id: " + userId + ") updated successfully.",
                userService.updateUser(user)
        );
    }

    @PutMapping("/{userId}/skillLevels/{levelId}")
    @PreAuthorize("hasAuthority('user:update') or #userId == authentication.principal.id")
    public ApiResponse<SkillLevel> updateSkillLevel(
            @RequestBody SkillLevel skillLevel,
            @PathVariable(value = "userId") Integer userId,
            @PathVariable(value = "levelId") Integer skillLevelId
    ) {
        skillLevel.setId(skillLevelId);
        return new ApiResponse<>(
                HttpStatus.OK,
                "Skill Level (id: " + skillLevelId + ") of user (id: " + userId + ") updated successfully.",
                userService.createOrUpdateSkillLevel(userId, skillLevel)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ApiResponse<Void> deleteUser(@PathVariable(value = "id") Integer userId) {
        userService.deleteUser(userId);
        return new ApiResponse<>(
                HttpStatus.OK,
                "User deleted successfully.",
                null
        );
    }

    @DeleteMapping("/{userId}/skillLevels/{levelId}")
    @PreAuthorize("hasAuthority('user:update') or #userId == authentication.principal.id")
    public ApiResponse<Void> deleteSkillLevel(
            @PathVariable(value = "userId") Integer userId,
            @PathVariable(value = "levelId") Integer skillLevelId
    ) {
        userService.deleteSkillLevel(userId, skillLevelId);
        return new ApiResponse<>(
                HttpStatus.OK,
                "Skill Level of user (id: " + userId + ") deleted successfully.",
                null
        );
    }
}
