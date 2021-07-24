package com.netcracker.skillstable.model.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department extends OrgItem {
    @Getter private static final Integer entTypeId = 4;

    private Set<Team> teams = new HashSet<>();
    @Getter private static final Integer teamRefId = 4;

    private Set<User> membersNoTeam = new HashSet<>();
    @Getter private static final Integer memberRefId = 20;

    public Department(Integer id, String name, String about) {
        super(id, name, about);
    }

    public Department(OrgItem orgItem) {
        super(orgItem.id, orgItem.name, orgItem.about);
    }

    public Department toDepartNoRefs() {
        return new Department(this.id, this.name, this.about);
    }

    public boolean addTeam(Team newTeam) {
        return teams.add(newTeam);
    }

    public boolean deleteTeam(Team team) {
        return teams.remove(team);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
