package com.netcracker.skillstable.model;

import javax.persistence.*;

@Entity(name="Parameter")
@Table (name = "parameters")
public class Parameter {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(
            name = "id_pk",
            updatable = false,
            nullable = false,
            columnDefinition = "SERIAL"
    )
    private Integer id;

    @ManyToOne(targetEntity = EAVObject.class)
    @JoinColumn(name = "ent_id")
    private EAVObject eavObject;

    @Column(
            name = "attr_id",
            nullable = false,
            columnDefinition = "INT"
    )
    private Integer attrId;

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

    public Parameter(Integer attrId, String attrValueTxt) {
        this.attrId = attrId;
        this.attrValueTxt = attrValueTxt;
        this.attrValueInt = null;
    }

    public Parameter(Integer attrId, Integer attrValueInt) {
        this.attrId = attrId;
        this.attrValueTxt = null;
        this.attrValueInt = attrValueInt;
    }

    public Parameter(Integer attrId, String attrValueTxt, Integer attrValueInt) {
        this.attrId = attrId;
        this.attrValueTxt = attrValueTxt;
        this.attrValueInt = attrValueInt;
    }

    public Parameter(EAVObject eavObject, Integer attrId, String attrValueTxt) {
        this(attrId, attrValueTxt);
        this.eavObject = eavObject;
    }

    public Parameter(EAVObject eavObject, Integer attrId, Integer attrValueInt) {
        this(attrId, attrValueInt);
        this.eavObject = eavObject;
    }

    public Parameter(EAVObject eavObject, Integer attrId, String attrValueTxt, Integer attrValueInt) {
        this(attrId, attrValueTxt, attrValueInt);
        this.eavObject = eavObject;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /*public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }*/

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
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
