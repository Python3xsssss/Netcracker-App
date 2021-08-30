package com.netcracker.skillstable.controller;

import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.model.dto.enumeration.Role;
import com.netcracker.skillstable.service.dto.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private UserService userService;

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('role:read')")
    public ResponseEntity<Role[]> getAllRoles(
    ) {
        return ResponseEntity.ok(Role.values());
    }

    @PostMapping("/users/{id}/role")
    @PreAuthorize("hasAuthority('role:update')")
    public ResponseEntity<User> addRoleToUser(
            @PathVariable(value = "id") Integer userId,
            @RequestBody RoleRequest roleRequest
    ) {
        String roleName = roleRequest.getRoleName();

        return ResponseEntity.ok(userService.addRole(userId, roleName));
    }

    @DeleteMapping("/users/{id}/role/{roleName}")
    @PreAuthorize("hasAuthority('role:delete')")
    public ResponseEntity<Void> deleteRoleFromUser(
            @PathVariable(value = "id") Integer userId,
            @PathVariable(value = "roleName") String roleName
    ) {
        userService.deleteRole(userId, roleName);
        return ResponseEntity.ok().build();
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class RoleRequest {
    private User user;
    private String roleName;
}