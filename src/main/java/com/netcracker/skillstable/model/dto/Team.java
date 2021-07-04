package com.netcracker.skillstable.model.dto;

import java.util.HashSet;
import java.util.Set;

public class Team extends OrgItem {
    private static final Integer entTypeId = 5;
    private Set<User> members = new HashSet<>();
    private static final Integer memberRefId = 20;


    public Team() {
    }

    public Team(Integer id, String name) {

    }

    public Team(String name, String description, User leader, Department department) {
        super(name, description, leader, department);
    }

    public Team(String name, String description, User leader, Department department, Set<User> members) {
        super(name, description, leader, department);
        this.members = members;
    }


    public static Integer getEntTypeId() {
        return entTypeId;
    }

    public static Integer getMemberRefId() {
        return memberRefId;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public boolean addMember(User newMember) {
        return members.add(newMember);
    }

    public boolean deleteMember(User member) {
        return members.remove(member);
    }
}
