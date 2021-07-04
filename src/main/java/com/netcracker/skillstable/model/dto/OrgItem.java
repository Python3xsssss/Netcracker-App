package com.netcracker.skillstable.model.dto;

public class OrgItem {
    protected Integer id;
    private static final Integer entTypeId = 3;
    protected String name;

    protected String about;
    private static final Integer aboutId = 25;

    protected User leader;
    private static final Integer leaderRefId = 17;

    protected Object superior;
    private static final Integer superiorRefId = 19;


    public OrgItem() {
    }

    public OrgItem(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public OrgItem(String name, String about) {
        this.name = name;
        this.about = about;
    }

    public OrgItem(String name, String about, User leader, Object superior) {
        this.name = name;
        this.about = about;
        this.leader = leader;
        this.superior = superior;
    }


    public static Integer getEntTypeId() {
        return entTypeId;
    }

    public static Integer getAboutId() {
        return aboutId;
    }

    public static Integer getLeaderRefId() {
        return leaderRefId;
    }

    public static Integer getSuperiorRefId() {
        return superiorRefId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
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
