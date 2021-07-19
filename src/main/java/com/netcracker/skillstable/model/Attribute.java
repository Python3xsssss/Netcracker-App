package com.netcracker.skillstable.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="Attribute")
@Table (name = "attributes")
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

    @Column(
            name = "attr_type",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String type;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return id.equals(attribute.id) && Objects.equals(name, attribute.name) && type.equals(attribute.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type);
    }
}
