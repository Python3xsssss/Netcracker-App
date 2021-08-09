package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.ApiResponse;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.DepartmentService;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    private void updateLeader(Department department) {
        if (department.getLeader() != null && department.getLeader().getId() != null) {
            User leader = userService.getUserById(department.getLeader().getId());
            if (!department.equals(leader.getDepartment())) {
                leader.setDepartment(department);
                userService.updateUser(leader);
            }
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('depart:create')")
    public ApiResponse<Department> saveDepart(@RequestBody Department department) {
        Department createdDepartment = departmentService.createDepartment(department);
        updateLeader(createdDepartment);

        return new ApiResponse<>(
                HttpStatus.OK,
                "Department saved successfully.",
                createdDepartment
        );
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<List<Department>> getAllDeparts() {
        return new ApiResponse<>(
                HttpStatus.OK,
                "Department list fetched successfully.",
                departmentService.getAllDepartments()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('depart:read')")
    public ApiResponse<Department> getDepart(@PathVariable(value = "id") Integer departId) {
        return new ApiResponse<>(
                HttpStatus.OK,
                "Department fetched successfully.",
                departmentService.getDepartmentById(departId)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('depart:update')")
    public ApiResponse<Department> updateDepart(
            @RequestBody Department department,
            @PathVariable(value = "id") Integer departId
            ) {
        Department updatedDepartment = departmentService.updateDepartment(department);
        updateLeader(updatedDepartment);

        return new ApiResponse<>(
                HttpStatus.OK,
                "Department updated successfully.",
                updatedDepartment
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('depart:delete')")
    public ApiResponse<Void> deleteDepart(@PathVariable(value = "id") Integer departId) {
        departmentService.deleteDepartment(departId);
        return new ApiResponse<>(
                HttpStatus.OK,
                "Department deleted successfully.",
                null
        );
    }
}
