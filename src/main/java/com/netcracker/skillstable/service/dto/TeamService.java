package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.exception.ResourceAlreadyExistsException;
import com.netcracker.skillstable.exception.ResourceNotFoundException;
import com.netcracker.skillstable.model.eav.EAVObject;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.service.eav.EAVService;
import com.netcracker.skillstable.service.eav.MetamodelService;
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
        EAVObject teamEavObj;
        try {
            teamEavObj = eavService.createEAVObj(teamConverter.dtoToEavObj(team));
        } catch (ResourceAlreadyExistsException exception) {
            throw new ResourceAlreadyExistsException("Team '" + team.getName() + "' already exists!");
        }

        return teamConverter.eavObjToDto(teamEavObj);
    }

    public List<Team> getAllTeams() {
        return eavService
                .getAllByEntTypeId(Team.getEntTypeId())
                .stream()
                .map(teamConverter::eavObjToDto)
                .collect(Collectors.toList());
    }

    public Team getTeamById(Integer teamId) {
        EAVObject teamEavObj;
        try {
            teamEavObj = eavService.getEAVObjById(teamId);
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException("Team not found!");
        }

        return teamConverter.eavObjToDto(teamEavObj);
    }

    public Team updateTeam(Team team) {
        EAVObject teamEavObj;
        try {
            teamEavObj = eavService.updateEAVObj(teamConverter.dtoToEavObj(team), team.getId());
        } catch (ResourceNotFoundException exception) {
            throw new ResourceNotFoundException("Team '" + team.getName() + "' not found!");
        } catch (ResourceAlreadyExistsException exception) {
            throw new ResourceAlreadyExistsException("Team '" + team.getName() + "' already exists!");
        }

        return teamConverter.eavObjToDto(teamEavObj);
    }

    public void deleteTeam(Integer teamId) {
        eavService.deleteEAVObj(teamId);
    }
}
