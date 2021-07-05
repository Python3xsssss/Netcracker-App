package com.netcracker.skillstable.model;

import javax.persistence.*;

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


    public Attribute() {

    }

    public Attribute(String name, String type, String descr) {
        this.name = name;
        this.type = type;
        this.descr = descr;
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
}
