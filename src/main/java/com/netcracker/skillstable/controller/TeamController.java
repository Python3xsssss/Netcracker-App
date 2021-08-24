package com.netcracker.skillstable.controller;

import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.TeamService;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    private void updateLeader(Team team) {
        if (team.getLeader() != null && team.getLeader().getId() != null) {
            User leader = userService.getUserById(team.getLeader().getId());
            if (!team.equals(leader.getTeam())) {
                leader.setTeam(team);
                userService.updateUser(leader);
            }
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('team:create')")
    public ResponseEntity<Team> saveTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        updateLeader(createdTeam);

        return ResponseEntity.ok(createdTeam);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CREATOR', 'ADMIN', 'TEAMLEAD')")
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('team:read')")
    public ResponseEntity<Team> getTeam(@PathVariable(value = "id") Integer teamId) {
        return ResponseEntity.ok(teamService.getTeamById(teamId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('team:update')")
    public ResponseEntity<Team> updateTeam(@RequestBody Team team) {
        Team updatedTeam = teamService.updateTeam(team);
        updateLeader(updatedTeam);

        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('team:delete')")
    public ResponseEntity<Void> deleteTeam(@PathVariable(value = "id") Integer teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.ok().build();
    }
}
