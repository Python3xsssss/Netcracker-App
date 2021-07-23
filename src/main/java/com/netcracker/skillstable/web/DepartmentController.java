package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.ApiResponse;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.service.dto.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ApiResponse<Department> saveDepart(@RequestBody Department department) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department saved successfully.",
                departmentService.createDepartment(department)
        );
    }

    @GetMapping
    public ApiResponse<List<Department>> getAllDeparts() {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department list fetched successfully.",
                departmentService.getAllDepartments()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<Optional<Department>> getDepart(@PathVariable(value = "id") Integer departId) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department fetched successfully.",
                departmentService.getDepartmentById(departId)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Department> updateDepart(
            @RequestBody Department department,
            @PathVariable(value = "id") Integer departId
            ) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department updated successfully.",
                departmentService.updateDepartment(department)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDepart(@PathVariable(value = "id") Integer departId) {
        departmentService.deleteDepartment(departId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Department deleted successfully.",
                null
        );
    }
}
