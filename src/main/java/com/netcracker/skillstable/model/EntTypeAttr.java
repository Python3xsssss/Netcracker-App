package com.netcracker.skillstable.model;

import javax.persistence.*;

@Entity(name="EntTypeAttr")
@Table (name = "entity_type_attr")
public class EntTypeAttr {
    @Id
    @Column(
            name = "id_pk",
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
            name = "attr_id",
            nullable = false,
            columnDefinition = "INT"
    )
    private Long attrId;


    public EntTypeAttr() {
    }

    public EntTypeAttr(Long entTypeId, Long attrId) {
        this.entTypeId = entTypeId;
        this.attrId = attrId;
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

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }
}
