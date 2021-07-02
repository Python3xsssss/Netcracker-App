package com.netcracker.skillstable.model.dto;

import java.util.HashSet;
import java.util.Set;

public class OrgItem {
    protected Long id;
    protected String name;
    protected String description;

    protected User leader;

    protected Object superior;


    public OrgItem() {
    }



    public OrgItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public OrgItem(String name, String description, User leader, Object superior) {
        this.name = name;
        this.description = description;
        this.leader = leader;
        this.superior = superior;
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

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public Object getSuperior() {
        return superior;
    }

    public void setSuperior(Object superior) {
        this.superior = superior;
    }
}
