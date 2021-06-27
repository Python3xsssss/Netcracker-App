package com.netcracker.skillstable.model;

import javax.persistence.*;

@Entity(name="Parameter")
@Table (name = "parameters")
public class Parameter {
    @Id
    @Column(
            name = "id_pk",
            updatable = false,
            nullable = false,
            columnDefinition = "SERIAL"
    )
    private Long id;

    @ManyToOne(targetEntity = EAVObject.class)
    @JoinColumn(name = "ent_id")
    private EAVObject eavObject;
    /*@Column(
            name = "ent_id",
            nullable = false,
            columnDefinition = "INT"
    )
    private Long entId;*/

    @Column(
            name = "attr_id",
            nullable = false,
            columnDefinition = "INT"
    )
    private Long attrId;

    @Column(
            name = "attr_value_txt",
            columnDefinition = "TEXT"
    )
    private String attrValueTxt;

    @Column(
            name = "attr_value_int",
            columnDefinition = "INT"
    )
    private Integer attrValueInt;


    public Parameter() {

    }


    public Parameter(Long entId, Long attrId, String attrValueTxt, Integer attrValueInt) {
        //this.entId = entId;
        this.attrId = attrId;
        this.attrValueTxt = attrValueTxt;
        this.attrValueInt = attrValueInt;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }*/

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getAttrValueTxt() {
        return attrValueTxt;
    }

    public void setAttrValueTxt(String attrValueTxt) {
        this.attrValueTxt = attrValueTxt;
    }

    public Integer getAttrValueInt() {
        return attrValueInt;
    }

    public void setAttrValueInt(Integer attrValueInt) {
        this.attrValueInt = attrValueInt;
    }
}
