package com.netcracker.skillstable.utils;

import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.DepartmentService;
import com.netcracker.skillstable.service.dto.TeamService;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizeHelper {
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departService;
    @Autowired
    private TeamService teamService;

    public boolean checkTeamIdentity(User user, Integer teamId) {
        Team team = teamService.getTeamById(teamId);
        return (team != null) && team.equals(user.getTeam());
    }

    public boolean checkDepartIdentity(User user, Integer departmentId) {
        Department department = departService.getDepartmentById(departmentId);
        return (department != null) && department.equals(user.getDepartment());
    }

    public boolean checkTeamLeader(User user, Integer teamId) {
        Team team = teamService.getTeamById(teamId);
        return (team != null) && team.equals(user.getTeam()) && user.equals(team.getLeader());
    }

    public boolean checkDepartLeader(User user, Integer departmentId) {
        Department department = departService.getDepartmentById(departmentId);
        return (department != null) && department.equals(user.getDepartment()) && user.equals(department.getLeader());
    }


}
