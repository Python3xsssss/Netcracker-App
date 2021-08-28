package com.netcracker.skillstable.controller;

import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.model.dto.enumeration.Role;
import com.netcracker.skillstable.service.dto.TeamService;
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
@RequestMapping("/api/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    private void updateLeaderProfile(Integer leaderId, Team team) {
        User newLeader = userService.getUserById(leaderId);
        newLeader.setDepartment(new Department(team.getSuperior()));
        newLeader.setTeam(team);
        userService.updateUser(newLeader);
        userService.addRole(leaderId, Role.TEAMLEAD.name());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('team:create')")
    public ResponseEntity<Team> createTeam(@RequestBody @Valid Team team, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }

        Team createdTeam = teamService.createTeam(team);
        User leader = createdTeam.getLeader();
        if (leader != null && leader.getId() != null) {
            updateLeaderProfile(leader.getId(), createdTeam);
        }

        return ResponseEntity.ok(createdTeam);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CREATOR', 'ADMIN', 'TEAMLEAD', 'DEPARTLEAD')")
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('team:read')")
    public ResponseEntity<Team> getTeam(@PathVariable(value = "id") Integer teamId) {
        return ResponseEntity.ok(teamService.getTeamById(teamId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('team:update') " +
            "or @authorizeHelper.checkTeamIdentity(authentication.principal, #team)")
    public ResponseEntity<Team> updateTeam(
            @PathVariable(value = "id") Integer teamId,
            @RequestBody Team team,
            BindingResult bindingResult
    ) {
        if (!teamId.equals(team.getId())) {
            throw new ResourceNotFoundException("Wrong team id!");
        }
        if (bindingResult.hasErrors()) {
            ValidationHelper.generateValidationException(bindingResult);
        }

        Team updatedTeam = teamService.updateTeam(team);
        User leader = updatedTeam.getLeader();
        if (leader != null && leader.getId() != null) {
            updateLeaderProfile(leader.getId(), updatedTeam);
        }
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('team:delete')")
    public ResponseEntity<Void> deleteTeam(@PathVariable(value = "id") Integer teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.ok().build();
    }
}
