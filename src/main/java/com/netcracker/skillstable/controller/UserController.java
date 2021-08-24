package com.netcracker.skillstable.controller;

import com.google.common.collect.Ordering;
import com.netcracker.skillstable.model.dto.SkillLevel;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.UserService;
import com.netcracker.skillstable.utils.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
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
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("/{id}/skillLevels")
    @PreAuthorize("hasAuthority('user:update') or #userId == authentication.principal.id")
    public ResponseEntity<SkillLevel> saveSkillLevel(
            @RequestBody @Valid SkillLevel skillLevel,
            @PathVariable(value = "id") Integer userId,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }
        return ResponseEntity.ok(userService.createOrUpdateSkillLevel(userId, skillLevel));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CREATOR')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Integer userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:update') or #userId == authentication.principal.id")
    public ResponseEntity<User> updateUser(
            @PathVariable(value = "id") Integer userId,
            @RequestBody @Valid User user,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PutMapping("/{userId}/skillLevels/{levelId}")
    @PreAuthorize("hasAuthority('user:update') or #userId == authentication.principal.id")
    public ResponseEntity<SkillLevel> updateSkillLevel(
            @PathVariable(value = "userId") Integer userId,
            @PathVariable(value = "levelId") Integer skillLevelId,
            @RequestBody SkillLevel skillLevel,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }
        skillLevel.setId(skillLevelId);
        return ResponseEntity.ok(userService.createOrUpdateSkillLevel(userId, skillLevel));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/skillLevels/{levelId}")
    @PreAuthorize("hasAuthority('user:update') or #userId == authentication.principal.id")
    public ResponseEntity<Void> deleteSkillLevel(
            @PathVariable(value = "userId") Integer userId,
            @PathVariable(value = "levelId") Integer skillLevelId
    ) {
        userService.deleteSkillLevel(userId, skillLevelId);
        return ResponseEntity.ok().build();
    }
}
