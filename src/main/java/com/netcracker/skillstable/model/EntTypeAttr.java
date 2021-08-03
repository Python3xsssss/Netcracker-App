package com.netcracker.skillstable.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="EntTypeAttr")
@Table (name = "entity_type_attr")
@Getter
@Setter
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
}
