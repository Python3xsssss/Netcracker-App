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
    private Long attrValueInt;


    public Parameter() {

    }

    public Parameter(Long attrId, String attrValueTxt) {
        this.attrId = attrId;
        this.attrValueTxt = attrValueTxt;
        this.attrValueInt = null;
    }

    public Parameter(Long attrId, Long attrValueInt) {
        this.attrId = attrId;
        this.attrValueTxt = null;
        this.attrValueInt = attrValueInt;
    }

    public Parameter(Long attrId, String attrValueTxt, Long attrValueInt) {
        this.attrId = attrId;
        this.attrValueTxt = attrValueTxt;
        this.attrValueInt = attrValueInt;
    }

    public Parameter(EAVObject eavObject, Long attrId, String attrValueTxt) {
        this(attrId, attrValueTxt);
        this.eavObject = eavObject;
    }

    public Parameter(EAVObject eavObject, Long attrId, Long attrValueInt) {
        this(attrId, attrValueInt);
        this.eavObject = eavObject;
    }

    public Parameter(EAVObject eavObject, Long attrId, String attrValueTxt, Long attrValueInt) {
        this(attrId, attrValueTxt, attrValueInt);
        this.eavObject = eavObject;
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

    public Long getAttrValueInt() {
        return attrValueInt;
    }

    public void setAttrValueInt(Long attrValueInt) {
        this.attrValueInt = attrValueInt;
    }
}
