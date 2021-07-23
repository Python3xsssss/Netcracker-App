package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.dto.Department;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.TeamConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;
    @Autowired
    private TeamConverter teamConverter;


    @Transactional
    public Team createTeam(Team team) {
        return teamConverter.eavObjToDto(eavService.createEAVObj(
                teamConverter.dtoToEavObj(
                        team,
                        metamodelService.getEntityTypeByEntTypeId(Team.getEntTypeId())
                )
        ));
    }

    @Transactional
    public List<Team> getAllTeams() {
        return eavService
                .getAllByEntTypeId(Team.getEntTypeId())
                .stream()
                .map(teamConverter::eavObjToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<Team> getTeamById(Integer teamId) {
        EAVObject teamEavObj = eavService.getEAVObjById(teamId);

        return Optional.of(teamConverter.eavObjToDto(teamEavObj));
    }

    @Transactional
    public Team updateTeam(Team team, Integer teamId) {
        EAVObject dtoEavObj = teamConverter.dtoToEavObj(
                team,
                metamodelService.getEntityTypeByEntTypeId(Team.getEntTypeId())
        );

        return teamConverter.eavObjToDto(eavService.updateEAVObj(dtoEavObj, teamId));
    }

    @Transactional
    public void deleteTeam(Integer teamId) {
        Optional<Team> optionalTeam = this.getTeamById(teamId);
        if (optionalTeam.isEmpty()) {
            return;
        }

        Team team = optionalTeam.get();
        for (User user : team.getMembers()) {
            user.setTeam(null);
        }

        eavService.deleteEAVObj(teamId);
    }
}
