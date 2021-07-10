package com.netcracker.skillstable.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Team extends OrgItem {
    @Getter private static final Integer entTypeId = 5;
    private Set<User> members = new HashSet<>();
    @Getter private static final Integer memberRefId = 20;


    public Team() {
    }

    @Builder
    public Team(Integer id, String name, String about, User leader, Object department, Set<User> members) {
        super(name, about, leader, department);
        this.id = id;
        this.members = members;
    }

    public boolean addMember(User newMember) {
        return members.add(newMember);
    }

    public boolean deleteMember(User member) {
        return members.remove(member);
    }
}
