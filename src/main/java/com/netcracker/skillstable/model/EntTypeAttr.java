package com.netcracker.skillstable.model;

import javax.persistence.*;

@Entity(name="EntTypeAttr")
@Table (name = "entity_type_attr")
public class EntTypeAttr {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(
            name = "id_pk",
            updatable = false,
            nullable = false,
            columnDefinition = "SERIAL"
    )
    private Integer id;

    @ManyToOne(targetEntity = EntityType.class)
    @JoinColumn(name = "ent_type_id")
    private EntityType entityType;

    @ManyToOne(targetEntity = Attribute.class)
    @JoinColumn(name = "attr_id")
    private Attribute attribute;


    public EntTypeAttr() {
    }

    public EntTypeAttr(EntityType entityType, Attribute attribute) {
        this.entityType = entityType;
        this.attribute = attribute;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EntityType getEntType() {
        return entityType;
    }

    public Attribute getAttribute() {
        return attribute;
    }
}
