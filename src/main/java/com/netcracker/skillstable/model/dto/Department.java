package com.netcracker.skillstable.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Department extends OrgItem {
    @Getter private static final Integer entTypeId = 4;
    private Set<Team> teams = new HashSet<>();
    @Getter private static final Integer teamRefId = 22;


    public Department() {
    }

    @Builder
    public Department(Integer id, String name, String about, User leader, OrgItem superior, Set<Team> teams) {
        super(id, name, about, leader, superior);
        this.teams = teams;
    }

    public boolean addTeam(Team newTeam) {
        return teams.add(newTeam);
    }

    public boolean deleteTeam(Team team) {
        return teams.remove(team);
    }
}
