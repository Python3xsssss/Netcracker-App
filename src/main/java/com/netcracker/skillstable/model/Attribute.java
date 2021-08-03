package com.netcracker.skillstable.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="Attribute")
@Table (name = "attributes")
@Getter
@Setter
public class Attribute {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(
            name = "attr_id",
            updatable = false,
            nullable = false,
            columnDefinition = "SERIAL"
    )
    private Integer id;

    @Column(
            name = "attr_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @ManyToOne(targetEntity = AttrType.class)
    @JoinColumn(name="attr_type_id")
    private AttrType attrType;

    @Column(
            name = "attr_descr",
            columnDefinition = "TEXT"
    )
    private String descr;

    @Column(
            name = "attr_multiple",
            columnDefinition = "BOOLEAN"
    )
    private Boolean multiple;


    public Attribute() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return id.equals(attribute.id) && Objects.equals(name, attribute.name) && attrType.equals(attribute.attrType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, attrType);
    }
}
