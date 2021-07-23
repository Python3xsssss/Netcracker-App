package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.TeamConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TeamService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;
    @Autowired
    private TeamConverter teamConverter;


    public Team createTeam(Team team) {
        return teamConverter.eavObjToDto(eavService.createEAVObj(
                teamConverter.dtoToEavObj(
                        team,
                        metamodelService.getEntityTypeByEntTypeId(Team.getEntTypeId())
                )
        ));
    }

    public List<Team> getAllTeams() {
        return eavService
                .getAllByEntTypeId(Team.getEntTypeId())
                .stream()
                .map(teamConverter::eavObjToDto)
                .collect(Collectors.toList());
    }

    public Team getTeamById(Integer teamId) {
        return teamConverter.eavObjToDto(eavService.getEAVObjById(teamId));
    }

    public Team updateTeam(Team team) {
        EAVObject dtoEavObj = teamConverter.dtoToEavObj(
                team,
                metamodelService.getEntityTypeByEntTypeId(Team.getEntTypeId())
        );

        return teamConverter.eavObjToDto(eavService.updateEAVObj(dtoEavObj, team.getId()));
    }

    public void deleteTeam(Integer teamId) {
        eavService.deleteEAVObj(teamId);
    }
}
