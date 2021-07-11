package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.ApiResponse;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.service.dto.DepartmentService;
import com.netcracker.skillstable.service.dto.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping
    public ApiResponse<Team> saveTeam(@RequestBody Team team) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Team saved successfully.",
                teamService.createTeam(team)
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
    public ApiResponse<Optional<Team>> getTeam(@PathVariable(value = "id") Integer teamId) {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Team fetched successfully.",
                teamService.getTeamById(teamId)
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
