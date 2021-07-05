package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.DepartmentService;
import com.netcracker.skillstable.service.dto.TeamService;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/data")
public class DtoController {
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private TeamService teamService;

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{userId}")
    public Optional<User> getSpecificUser(@PathVariable(value="userId") Integer userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/department")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/department/{departId}")
    public Optional<Department> getSpecificDepart(@PathVariable(value="departId") Integer departId) {
        return departmentService.getDepartmentById(departId);
    }

    @GetMapping("/team")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/team/{teamId}")
    public Optional<Team> getSpecificTeam(@PathVariable(value="teamId") Integer teamId) {
        return teamService.getTeamById(teamId);
    }

}
