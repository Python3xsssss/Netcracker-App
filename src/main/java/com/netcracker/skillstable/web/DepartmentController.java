package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.service.dto.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/data/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    @ResponseBody
    public Optional<Department> getSpecificDepartment(@RequestParam(name = "id", required = true) Optional<Integer> id) {
        return id.flatMap(inputId -> departmentService.getDepartmentById(inputId));
    }
}
