package com.netcracker.skillstable.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="EntityType")
@Table (name = "entity_types")
@Getter
@Setter
public class EntityType {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(
            name = "ent_type_id",
            updatable = false,
            nullable = false,
            columnDefinition = "SERIAL"
    )
    private Integer id;

    @Column(
            name = "ent_type_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "ent_type_descr",
            columnDefinition = "TEXT"
    )
    private String descr;

    @Column(
            name = "ent_parent_id"
    )
    private Integer entParentId;

    public EntityType() {

    }

    public EntityType(String name, String descr, Integer entParentId) {
        this.name = name;
        this.descr = descr;
        this.entParentId = entParentId;
    }

    @Override
    public String toString() {
        return "EntityType{" +
                "name='" + name + '\'' +
                '}';
    }
}
