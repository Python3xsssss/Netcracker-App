package com.netcracker.skillstable.utils;

import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.DepartmentService;
import com.netcracker.skillstable.service.dto.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LeaderValidator implements ConstraintValidator<Leader, User> {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private TeamService teamService;

    @Override
    public void initialize(Leader leaderAnnotation) {
    }

    @Override
    public boolean isValid(User leader, ConstraintValidatorContext context) {
        Department leadersDepart = null;
        if (leader.getDepartment() != null && leader.getDepartment().getId() != null) {
            leadersDepart = departmentService.getDepartmentById(leader.getDepartment().getId());
        }

        Team leadersTeam = null;
        if (leader.getTeam() != null && leader.getTeam().getId() != null) {
            leadersTeam = teamService.getTeamById(leader.getTeam().getId());
        }

        return (leadersDepart == null || !leader.equals(leadersDepart.getLeader()))
                && (leadersTeam == null || !leader.equals(leadersTeam.getLeader()));
    }
}
