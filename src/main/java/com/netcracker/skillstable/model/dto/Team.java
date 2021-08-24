package com.netcracker.skillstable.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team extends OrgItem {
    @Getter private static final Integer entTypeId = 5;
    private Set<User> members = new HashSet<>();
    @Getter private static final Integer memberRefId = 20;

    public Team(Integer id, String name, String about) {
        super(id, name, about);
    }

    public Team toTeamNoRefs() {
        return new Team(this.id, this.name, this.about);
    }

    public boolean addMember(User newMember) {
        return members.add(newMember);
    }

    public boolean deleteMember(User member) {
        return members.remove(member);
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
