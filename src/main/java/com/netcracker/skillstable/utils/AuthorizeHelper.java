package com.netcracker.skillstable.utils;

import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import org.springframework.stereotype.Component;

@Component
public class AuthorizeHelper {
    public boolean checkTeamIdentity(User auth, Team team) {
        return (team != null) && team.equals(auth.getTeam()) && auth.equals(team.getLeader());
    }

    public boolean checkDepartIdentity(User auth, Department department) {
        return (department != null) && department.equals(auth.getDepartment()) && auth.equals(department.getLeader());
    }
}
