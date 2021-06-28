package com.netcracker.skillstable.model;

import javax.persistence.*;

@Entity(name="EntityObj")
@Table (name = "entities")
public class OldEntityObj {
    @Id
    @Column(
            name = "ent_id",
            updatable = false,
            nullable = false,
            columnDefinition = "SERIAL"
    )
    private Long id;

    @Column(
            name = "ent_type_id",
            nullable = false,
            columnDefinition = "INT"
    )
    private Long entTypeId;

    @Column(
            name = "ent_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String entName;

    public OldEntityObj() {

    }

    public OldEntityObj(Long entTypeId, String entName) {
        this.entTypeId = entTypeId;
        this.entName = entName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntTypeId() {
        return entTypeId;
    }

    public void setEntTypeId(Long entTypeId) {
        this.entTypeId = entTypeId;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }
}
