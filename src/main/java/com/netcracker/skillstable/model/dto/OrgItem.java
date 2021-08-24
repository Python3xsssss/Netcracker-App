package com.netcracker.skillstable.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
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


    public OrgItem(Integer id, String name, String about) {
        this.id = id;
        this.name = name;
        this.about = about;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrgItem orgItem = (OrgItem) o;
        return id.equals(orgItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
