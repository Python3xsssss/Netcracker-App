package com.netcracker.skillstable.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="AttrType")
@Table(name="attr_types")
public class AttrType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(
            name = "attr_type_id",
            updatable = false,
            nullable = false,
            columnDefinition = "SERIAL"
    )
    private Integer id;

    @Column(
            name = "attr_type",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String type;

    public AttrType() {
    }

    public AttrType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttrType)) return false;
        AttrType attrType = (AttrType) o;
        return id.equals(attrType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AttrType{" +
                "type='" + type + '\'' +
                '}';
    }
}
