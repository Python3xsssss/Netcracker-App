package com.netcracker.skillstable.model;

import javax.persistence.*;

@Entity(name="EAVObject")
@Table (name = "eav")
public class EAVObject {
    @Id
    @Column(
            name = "id_pk",
            updatable = false,
            nullable = false,
            columnDefinition = "SERIAL"
    )
    private Long id;

    @Column(
            name = "ent_id",
            nullable = false,
            columnDefinition = "INT"
    )
    private Long entId;

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


    public EAVObject() {

    }


    public EAVObject(Long entTypeId, Long attrId, String attrValueTxt, Integer attrValueInt) {
        this.entId = entTypeId;
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

    public Long getEntTypeId() {
        return entId;
    }

    public void setEntTypeId(Long entTypeId) {
        this.entId = entTypeId;
    }

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
