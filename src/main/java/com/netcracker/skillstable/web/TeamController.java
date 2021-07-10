package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.service.dto.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/create/team")
    public String createTeam() {
        return "createTeam";
    }

    @PostMapping("/create/team")
    public String addTeam(Team team) {
        teamService.createTeam(team);

        return "redirect:/";
    }
}
