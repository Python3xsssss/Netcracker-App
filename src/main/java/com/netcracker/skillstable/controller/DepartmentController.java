package com.netcracker.skillstable.controller;

import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.DepartmentService;
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
    public ResponseEntity<Department> saveDepart(
            @RequestBody @Valid Department department,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }

        Department createdDepartment = departmentService.createDepartment(department);
        updateLeader(createdDepartment);

        return ResponseEntity.ok(createdDepartment);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Department>> getAllDeparts() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('depart:read')")
    public ResponseEntity<Department> getDepart(@PathVariable(value = "id") Integer departId) {
        return ResponseEntity.ok(departmentService.getDepartmentById(departId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('depart:update')")
    public ResponseEntity<Department> updateDepart(
            @PathVariable(value = "id") Integer departId,
            @RequestBody Department department,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }

        Department updatedDepartment = departmentService.updateDepartment(department);
        updateLeader(updatedDepartment);

        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('depart:delete')")
    public ResponseEntity<Void> deleteDepart(@PathVariable(value = "id") Integer departId) {
        departmentService.deleteDepartment(departId);
        return ResponseEntity.ok().build();
    }
}
