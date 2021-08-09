package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.ApiResponse;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.model.dto.attr.Role;
import com.netcracker.skillstable.service.dto.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/users/{id}/role")
public class RoleController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('role:read')")
    public ApiResponse<Role[]> getAllRoles(
    ) {
        return new ApiResponse<>(
                HttpStatus.OK,
                "Roles fetched successfully.",
                Role.values()
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('role:update')")
    public ApiResponse<User> addRole(
            @PathVariable(value = "id") Integer userId,
            @RequestBody RoleRequest roleRequest
    ) {
        String roleName = roleRequest.getRoleName();
        User user = roleRequest.getUser();
        if (roleName.equals(Role.CREATOR.name())) {
            return new ApiResponse<>(
                    HttpStatus.FORBIDDEN,
                    "Role " + roleName + " cannot be added.",
                    null
            );
        }

        return new ApiResponse<>(
                HttpStatus.OK,
                "Role " + roleName + " added successfully.",
                userService.addRole(user, roleName)
        );
    }

    @DeleteMapping("/{roleName}")
    @PreAuthorize("hasAuthority('role:delete')")
    public ApiResponse<Void> deleteRole(
            @PathVariable(value = "id") Integer userId,
            @PathVariable(value = "roleName") String roleName
    ) {
        if (roleName.equals(Role.CREATOR.name())) {
            return new ApiResponse<>(
                    HttpStatus.FORBIDDEN,
                    "Role " + roleName + " cannot be deleted.",
                    null
            );
        }
        userService.deleteRole(userId, roleName);
        return new ApiResponse<>(
                HttpStatus.OK,
                "Role " + roleName + " deleted successfully.",
                null
        );
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class RoleRequest {
    private User user;
    private String roleName;
}