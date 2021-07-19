package com.netcracker.skillstable.model.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    protected OrgItem superior;
    @Getter private static final Integer superiorRefId = 19;


    public OrgItem() {
    }

    @Builder(builderMethodName = "OrgItemBuilder")
    public OrgItem(Integer id, String name, String about, User leader, OrgItem superior) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.leader = leader;
        this.superior = superior;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrgItem orgItem = (OrgItem) o;
        return id.equals(orgItem.id) && Objects.equals(name, orgItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
