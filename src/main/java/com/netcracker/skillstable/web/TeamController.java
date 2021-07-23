package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.ApiResponse;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.TeamService;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ApiResponse<Team> saveTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        updateLeader(createdTeam);

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Team saved successfully.",
                createdTeam
        );
    }

    @GetMapping
    public ApiResponse<List<Team>> getAllTeams() {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Team list fetched successfully.",
                teamService.getAllTeams()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<Team> getTeam(@PathVariable(value = "id") Integer teamId) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Team fetched successfully.",
                teamService.getTeamById(teamId)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Team> updateTeam(@RequestBody Team team) {
        Team updatedTeam = teamService.updateTeam(team);
        updateLeader(updatedTeam);

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Team updated successfully.",
                updatedTeam
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTeam(@PathVariable(value = "id") Integer teamId) {
        teamService.deleteTeam(teamId);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Team deleted successfully.",
                null
        );
    }
}
