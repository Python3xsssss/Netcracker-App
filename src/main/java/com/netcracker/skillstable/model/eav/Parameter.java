package com.netcracker.skillstable.model.eav;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Parameter")
@Table(name = "values")
@Getter
@Setter
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

    @ManyToOne(targetEntity = EAVObject.class, fetch=FetchType.LAZY)
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

    @Column(
            name = "attr_value_bool",
            columnDefinition = "BOOLEAN"
    )
    private Boolean attrValueBool;

    @ManyToOne(targetEntity = EAVObject.class, fetch=FetchType.LAZY)
    @JoinColumn(name = "attr_value_ref")
    private EAVObject referenced;


    public Parameter() {

    }

    public Parameter(EAVObject eavObject, Attribute attribute, String attrValueTxt) {
        this.attribute = attribute;
        this.attrValueTxt = attrValueTxt;
        this.eavObject = eavObject;
    }

    public Parameter(EAVObject eavObject, Attribute attribute, Integer attrValueInt) {
        this.attribute = attribute;
        this.attrValueInt = attrValueInt;
        this.eavObject = eavObject;
    }

    public Parameter(EAVObject eavObject, Attribute attribute, Boolean attrValueBool) {
        this.attribute = attribute;
        this.attrValueBool = attrValueBool;
        this.eavObject = eavObject;
    }

    public Parameter(EAVObject eavObject, Attribute attribute, EAVObject referenced) {
        this.attribute = attribute;
        this.referenced = referenced;
        this.eavObject = eavObject;
    }

    public void setParameterValues(Parameter parameter) {
        this.attrValueInt = parameter.attrValueInt;
        this.attrValueTxt = parameter.attrValueTxt;
        this.referenced = parameter.referenced;
        this.attrValueBool = parameter.attrValueBool;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "id=" + id +
                ", eavObject=" + eavObject.getEntName() +
                ", attribute=" + attribute.getName() +
                (attrValueTxt != null ? ", attrValueTxt='" + attrValueTxt + '\'' : "") +
                (attrValueInt != null ? ", attrValueInt=" + attrValueInt : "") +
                (attrValueBool != null ? ", attrValueBool=" + attrValueBool : "") +
                (referenced != null ? ", referenced=" + referenced.getEntName() : "") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parameter)) return false;
        Parameter parameter = (Parameter) o;
        return eavObject.equals(parameter.eavObject) && attribute.equals(parameter.attribute) && Objects.equals(attrValueTxt, parameter.attrValueTxt) && Objects.equals(attrValueInt, parameter.attrValueInt) && Objects.equals(attrValueBool, parameter.attrValueBool) && Objects.equals(referenced, parameter.referenced);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eavObject, attribute, attrValueTxt, attrValueInt, attrValueBool, referenced);
    }
}
