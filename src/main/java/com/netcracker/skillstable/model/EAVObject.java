package com.netcracker.skillstable.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="EAVObject")
@Table (name = "entities")
public class EAVObject {
    @Id
    @Column(
            name = "ent_id",
            updatable = false,
            nullable = false,
            columnDefinition = "SERIAL"
    )
    private Long id;

    @ManyToOne(targetEntity = EntityType.class)
    @JoinColumn(name = "ent_type_id")
    private EntityType entType;

    @Column(
            name = "ent_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String entName;

    @OneToMany(mappedBy="eavObject", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval = true)
    private List<Parameter> parameters = new ArrayList<>();


    public EAVObject() {

    }

    public EAVObject(String entName) {
        this.entName = entName;
    }

    public EAVObject(EntityType entType, String entName) {
        this.entType = entType;
        this.entName = entName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityType getEntType() {
        return entType;
    }

    public void setEntType(EntityType entType) {
        this.entType = entType;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public void addParameters(List<Parameter> inputParams) {
        parameters.addAll(inputParams);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public List<ParameterValue> getParametersByAttrId(Long attrId) { // id exception?
        List<ParameterValue> listOfValues = new ArrayList<>();
        for (Parameter parameter : parameters) {
            if (attrId.equals(parameter.getAttrId())) {
                listOfValues.add(new ParameterValue(
                        parameter.getAttrValueInt(),
                        parameter.getAttrValueTxt()
                ));
            }
        }

        return listOfValues;
    }

    public void setParameter(Long attrId, ParameterValue value) {
        for (Parameter parameter : parameters) {
            if (attrId.equals(parameter.getAttrId())) {  // multiple?
                parameter.setAttrValueInt(value.getValueInt());
                parameter.setAttrValueTxt(value.getValueStr());
            }
        }
    }

    public void deleteParameter(Long attrId) {
        parameters.removeIf(parameter -> attrId.equals(parameter.getAttrId()));
    }
}
