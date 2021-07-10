package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.service.dto.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/create/department")
    public String createDepartment() {
        return "createDepart";
    }

    @PostMapping("/create/department")
    public String addDepartment(Department department) {
        departmentService.createDepartment(department);

        return "redirect:/";
    }
}
