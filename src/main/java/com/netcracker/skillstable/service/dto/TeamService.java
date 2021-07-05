package com.netcracker.skillstable.service.dto;

import com.netcracker.skillstable.model.EAVObject;
import com.netcracker.skillstable.model.dto.Team;
import com.netcracker.skillstable.service.EAVService;
import com.netcracker.skillstable.service.MetamodelService;
import com.netcracker.skillstable.service.converter.TeamConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {
    @Autowired
    private EAVService eavService;
    @Autowired
    private MetamodelService metamodelService;


    public Integer createTeam(Team team) {
        return eavService.createEAVObj(
                TeamConverter.dtoToEavObj(
                        team,
                        metamodelService.getEntityTypeByEntTypeId(Team.getEntTypeId())
                )
        ).getId();
    }

    public List<Team> getAllTeams() {
        return eavService
                .getAllByEntTypeId(Team.getEntTypeId())
                .stream()
                .map(TeamConverter::eavObjToDto)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Optional<Team> getTeamById(Integer teamId) {
        Optional<EAVObject> optionalEavObj = eavService.getEAVObjById(teamId);
        if (!optionalEavObj.isPresent() || !Team.getEntTypeId().equals(optionalEavObj.get().getEntType().getId())) {
            return Optional.empty();
        }

        EAVObject teamEavObj = optionalEavObj.get();

        return TeamConverter.eavObjToDto(teamEavObj);
    }

    public void deleteTeam(Integer userId) {
        // todo
    }
}
