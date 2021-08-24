package com.netcracker.skillstable.controller;

import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.DepartmentService;
import com.netcracker.skillstable.service.dto.TeamService;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
public class DtoTestController {
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private TeamService teamService;


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public User getSpecificUser(@PathVariable(value="userId") Integer userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/users")
    public User create(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping("/users/{userId}")
    public void delete(@PathVariable("userId") int userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/departments/{departId}")
    public Department getSpecificDepart(@PathVariable(value="departId") Integer departId) {
        return departmentService.getDepartmentById(departId);
    }

    @GetMapping("/teams")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/teams/{teamId}")
    public Team getSpecificTeam(@PathVariable(value="teamId") Integer teamId) {
        return teamService.getTeamById(teamId);
    }

}
