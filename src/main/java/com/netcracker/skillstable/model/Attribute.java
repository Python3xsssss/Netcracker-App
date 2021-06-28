package com.netcracker.skillstable.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Attribute")
@Table (name = "attributes")
public class Attribute {
    @Id
    @Column(
            name = "attr_id",
            updatable = false,
            nullable = false,
            columnDefinition = "SERIAL"
    )
    private Long id;

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

    @OneToMany(mappedBy="attribute", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    private List<EntTypeAttr> entTypeAttrs = new ArrayList<>();


    public Attribute() {

    }

    public Attribute(String name, String type, String descr) {
        this.name = name;
        this.type = type;
        this.descr = descr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<EntTypeAttr> getEntTypeAttrs() {
        return entTypeAttrs;
    }
}
