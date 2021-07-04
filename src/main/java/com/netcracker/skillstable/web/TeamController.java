package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.service.dto.TeamService;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/data/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("")
    @ResponseBody
    public Optional<Team> getSpecificTeam(@RequestParam(name = "id", required = true) Optional<Integer> id) {
        return id.flatMap(inputId -> teamService.getTeamById(inputId));
    }
}
