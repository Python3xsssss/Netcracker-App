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

    public boolean checkTeamIdentity(User user, Team team) {
        if (team == null || team.getId() == null || user == null || user.getId() == null) {
            return false;
        }
        Team fetchedTeam = teamService.getTeamById(team.getId());
        User fetchedUser = userService.getUserById(user.getId());
        return fetchedTeam.equals(fetchedUser.getTeam());
    }

    public boolean checkDepartIdentity(User user, Department department) {
        if (department == null || department.getId() == null || user == null || user.getId() == null) {
            return false;
        }
        Department fetchedDepartment = departService.getDepartmentById(department.getId());
        User fetchedUser = userService.getUserById(user.getId());
        return fetchedDepartment.equals(fetchedUser.getDepartment());
    }

    public boolean checkTeamLeader(User user, Team team) {
        if (team == null || team.getId() == null || user == null || user.getId() == null) {
            return false;
        }
        Team fetchedTeam = teamService.getTeamById(team.getId());
        User fetchedUser = userService.getUserById(user.getId());
        return fetchedTeam.equals(fetchedUser.getTeam()) && fetchedUser.equals(fetchedTeam.getLeader());
    }

    public boolean checkDepartLeader(User user, Department department) {
        if (department == null || department.getId() == null || user == null || user.getId() == null) {
            return false;
        }
        Department fetchedDepartment = departService.getDepartmentById(department.getId());
        User fetchedUser = userService.getUserById(user.getId());
        return fetchedDepartment.equals(fetchedUser.getDepartment()) && fetchedUser.equals(fetchedDepartment.getLeader());
    }
}
