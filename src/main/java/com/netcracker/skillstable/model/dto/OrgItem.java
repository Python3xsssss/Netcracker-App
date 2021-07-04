package com.netcracker.skillstable.model.dto;

public class OrgItem {
    protected Long id;
    private static final Long entTypeId = 3L;
    protected String name;

    protected String about;
    private static final Long aboutId = 25L;

    protected User leader;
    private static final Long leaderRefId = 17L;

    protected Object superior;
    private static final Long superiorRefId = 19L;


    public OrgItem() {
    }

    public OrgItem(Long id, String name) {
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


    public static Long getEntTypeId() {
        return entTypeId;
    }

    public static Long getAboutId() {
        return aboutId;
    }

    public static Long getLeaderRefId() {
        return leaderRefId;
    }

    public static Long getSuperiorRefId() {
        return superiorRefId;
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
