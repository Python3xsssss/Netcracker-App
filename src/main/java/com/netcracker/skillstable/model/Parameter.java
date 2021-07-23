package com.netcracker.skillstable.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Parameter")
@Table(name = "parameters")
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(targetEntity = Attribute.class)
    @JoinColumn(name = "attr_id")
    private Attribute attribute;

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

    public Parameter(Attribute attribute, String attrValueTxt) {
        this.attribute = attribute;
        this.attrValueTxt = attrValueTxt;
        this.attrValueInt = null;
    }

    public Parameter(Attribute attribute, Integer attrValueInt) {
        this.attribute = attribute;
        this.attrValueTxt = null;
        this.attrValueInt = attrValueInt;
    }

    public Parameter(Attribute attribute, String attrValueTxt, Integer attrValueInt) {
        this.attribute = attribute;
        this.attrValueTxt = attrValueTxt;
        this.attrValueInt = attrValueInt;
    }

    public Parameter(EAVObject eavObject, Attribute attribute, String attrValueTxt) {
        this(attribute, attrValueTxt);
        this.eavObject = eavObject;
    }

    public Parameter(EAVObject eavObject, Attribute attribute, Integer attrValueInt) {
        this(attribute, attrValueInt);
        this.eavObject = eavObject;
    }

    public Parameter(EAVObject eavObject, Attribute attribute, String attrValueTxt, Integer attrValueInt) {
        this(attribute, attrValueTxt, attrValueInt);
        this.eavObject = eavObject;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ParameterValue getAttrValue() {
        return new ParameterValue(this.attrValueInt, this.attrValueTxt);
    }

    public void setAttrValue(ParameterValue attrValue) {
        this.attrValueInt = attrValue.getValueInt();
        this.attrValueTxt = attrValue.getValueTxt();
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


    public EAVObject getEavObject() {
        return eavObject;
    }

    public void setEavObject(EAVObject eavObject) {
        this.eavObject = eavObject;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "id=" + id +
                ", eavObjectId=" + ((eavObject != null) ? eavObject.getId() : "None") +
                ", attribute=" + ((attribute != null) ? attribute.getName() : "None") +
                ", attrValueTxt='" + attrValueTxt + '\'' +
                ", attrValueInt=" + attrValueInt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return eavObject.equals(parameter.eavObject) && attribute.equals(parameter.attribute) && Objects.equals(attrValueTxt, parameter.attrValueTxt) && Objects.equals(attrValueInt, parameter.attrValueInt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eavObject, attribute, attrValueTxt, attrValueInt);
    }
}
