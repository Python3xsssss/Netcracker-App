package com.netcracker.skillstable.controller;

import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.dto.SkillLevel;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.UserService;
import com.netcracker.skillstable.utils.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('user:create') " +
            "or hasRole('TEAMLEAD') " +
            "and @authorizeHelper.checkTeamLeader(authentication.principal, #user.team.id) " +
            "or hasRole('DEPARTLEAD') " +
            "and @authorizeHelper.checkDepartLeader(authentication.principal, #user.department.id)")
    public ResponseEntity<User> createUser(
            @RequestBody @Valid User user,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }

        return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("/{userId}/skillLevels")
    @PreAuthorize("hasAuthority('user:update') or #userId == authentication.principal.id")
    public ResponseEntity<SkillLevel> createOrUpdateSkillLevel(
            @RequestBody @Valid SkillLevel skillLevel,
            @PathVariable(value = "userId") Integer userId,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }
        return ResponseEntity.ok(userService.createOrUpdateSkillLevel(userId, skillLevel));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Integer userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:update') or #userId == authentication.principal.id " +
            "or hasRole('TEAMLEAD') " +
            "and @authorizeHelper.checkTeamLeader(authentication.principal, #user.team.id) " +
            "or hasRole('DEPARTLEAD') " +
            "and @authorizeHelper.checkDepartLeader(authentication.principal, #user.department.id)")
    public ResponseEntity<User> updateUser(
            @PathVariable(value = "id") Integer userId,
            @RequestBody @Valid User user,
            BindingResult bindingResult
    ) {
        if (!userId.equals(user.getId())) {
            throw new ResourceNotFoundException("Wrong user id!");
        }
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
        if (!skillLevelId.equals(skillLevel.getId())) {
            throw new ResourceNotFoundException("Wrong skill level id!");
        }
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }

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
        userService.deleteSkillLevel(skillLevelId);
        return ResponseEntity.ok().build();
    }
}
