package com.netcracker.skillstable.model.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgItem {
    protected Integer id;
    @Getter private static final Integer entTypeId = 3;
    protected String name;

    protected String about;
    @Getter private static final Integer aboutId = 25;

    protected User leader;
    @Getter private static final Integer leaderRefId = 17;

    protected Object superior;
    @Getter private static final Integer superiorRefId = 19;


    public OrgItem() {
    }

    @Builder(builderMethodName = "OrgItemBuilder")
    public OrgItem(String name, String about, User leader, Object superior) {
        this.name = name;
        this.about = about;
        this.leader = leader;
        this.superior = superior;
    }
}
