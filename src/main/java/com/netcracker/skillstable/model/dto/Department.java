package com.netcracker.skillstable.model.dto;

import java.util.HashSet;
import java.util.Set;

public class Department extends OrgItem {
    private static final Integer entTypeId = 4;
    private Set<Team> teams = new HashSet<>();
    private static final Integer teamRefId = 4;


    public Department() {
    }

    public Department(Integer id, String name) {
        super(id, name);
    }

    public Department(String name, String about, User leader, Object superior) {
        super(name, about, leader, superior);
    }

    public Department(String name, String about, User leader, Object superior, Set<Team> teams) {
        super(name, about, leader, superior);
        this.teams = teams;
    }

    public Department(Integer id, String name, String about, User leader, Object superior, Set<Team> teams) {
        this(name, about, leader, superior, teams);
        this.id = id;
    }


    public static Integer getEntTypeId() {
        return entTypeId;
    }

    public static Integer getTeamRefId() {
        return teamRefId;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public boolean addTeam(Team newTeam) {
        return teams.add(newTeam);
    }

    public boolean deleteTeam(Team team) {
        return teams.remove(team);
    }
}
