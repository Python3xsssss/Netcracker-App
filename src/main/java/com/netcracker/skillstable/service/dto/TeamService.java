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


    public Team createOrUpdateTeam(Team team) {
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

    public Optional<Team> getTeamById(Integer teamId) {
        Optional<EAVObject> optionalEavObj = eavService.getEAVObjById(teamId);
        if (!optionalEavObj.isPresent() || !Team.getEntTypeId().equals(optionalEavObj.get().getEntType().getId())) {
            return Optional.empty();
        }

        EAVObject teamEavObj = optionalEavObj.get();

        return Optional.of(teamConverter.eavObjToDto(teamEavObj));
    }

    public void deleteTeam(Integer teamId) {
        Optional<Team> optionalTeam = this.getTeamById(teamId);
        if (!optionalTeam.isPresent()) {
            return;
        }

        Team team = optionalTeam.get();
        for (User user : team.getMembers()) {
            user.setTeam(null);
        }

        eavService.deleteEAVObj(teamId);
    }
}
