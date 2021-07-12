package com.netcracker.skillstable.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity(name="EAVObject")
@Table (name = "entities")
public class EAVObject {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(
            name = "ent_id",
            updatable = false,
            nullable = false,
            columnDefinition = "SERIAL"
    )
    private Integer id;

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void addParameter(Parameter inputParam) {
        parameters.add(inputParam);
    }

    public void addParameters(List<Parameter> inputParams) {
        parameters.addAll(inputParams);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public Optional<ParameterValue> getParameterByAttrId(Integer attrId) {
        for (Parameter parameter : parameters) {
            if (attrId.equals(parameter.getAttrId())) {
                return Optional.of(new ParameterValue(
                        parameter.getAttrValueInt(),
                        parameter.getAttrValueTxt()
                ));
            }
        }

        return Optional.empty();
    }

    public List<ParameterValue> getMultipleParametersByAttrId(Integer attrId) { // id exception?
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

    public void setParameter(Integer attrId, ParameterValue value) {
        for (Parameter parameter : parameters) {
            if (attrId.equals(parameter.getAttrId())) {  // multiple?
                parameter.setAttrValueInt(value.getValueInt());
                parameter.setAttrValueTxt(value.getValueStr());
            }
        }
    }

    public void deleteParameter(Integer attrId) {
        parameters.removeIf(parameter -> attrId.equals(parameter.getAttrId()));
    }
}
