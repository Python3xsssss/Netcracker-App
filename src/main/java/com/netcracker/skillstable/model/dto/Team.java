package com.netcracker.skillstable.model.dto;

import java.util.HashSet;
import java.util.Set;

public class Team extends OrgItem{
    private Set<User> members = new HashSet<>();


    public Team() {
    }

    public Team(String name, String description, Department department) {
        this.name = name;
        this.description = description;
        this.superior = department;
    }

    public Team(String name, String description, Department department, Set<User> members) {
        this(name, description, department);
        this.members = members;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public Department getDepartment() {
        return (Department) superior;
    }

    public void setDepartment(Department department) {
        this.superior = department;
    }
}
