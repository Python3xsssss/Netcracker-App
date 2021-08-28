package com.netcracker.skillstable.model.eav;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity(name="EAVObject")
@Table (name = "entities")
@Getter
@Setter
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

    @OneToMany(mappedBy="eavObject", cascade=CascadeType.ALL, orphanRemoval = true)
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

    public EAVObject(Integer id, EntityType entType, String entName) {
        this.id = id;
        this.entType = entType;
        this.entName = entName;
    }


    public void addParameter(Parameter inputParam) {
        parameters.add(inputParam);
    }

    public void addParameters(List<Parameter> inputParams) {
        parameters.addAll(inputParams);
    }

    public Optional<Parameter> getParameterByAttrId(Integer attrId) {
        for (Parameter parameter : parameters) {
            if (attrId.equals(parameter.getAttribute().getId())) {
                return  Optional.of(parameter);
            }
        }

        return Optional.empty();
    }

    public List<Parameter> getMultipleParametersByAttrId(Integer attrId) { // id exception?
        List<Parameter> listOfValues = new ArrayList<>();
        for (Parameter parameter : parameters) {
            if (attrId.equals(parameter.getAttribute().getId())) {
                listOfValues.add(parameter);
            }
        }

        return listOfValues;
    }

    public List<Parameter> updateParameters(List<Parameter> inputParams) {
        List<Parameter> newParameters = new ArrayList<>();

        for (Parameter inputParam : inputParams) {
            boolean contains = false;
            for (ListIterator<Parameter> iter = this.parameters.listIterator(); iter.hasNext(); ) {
                Parameter param = iter.next();
                if (inputParam.getAttribute().equals(param.getAttribute())) {
                    if (param.getAttribute().getMultiple()) {
                        if (param.equals(inputParam)) {
                            contains = true;
                            break;
                        }
                    } else {
                        contains = true;
                        param.setParameterValues(inputParam);
                        iter.set(param);
                        break;
                    }
                }
            }
            if (!contains) {
                newParameters.add(inputParam);
            }
        }

        this.parameters.removeIf(param -> !inputParams.contains(param));

        return newParameters;
    }

    public void deleteParameter(Parameter parameter) {
        this.parameters.remove(parameter);
    }

    @Override
    public String toString() {
        return "EAVObject{" +
                "id=" + id +
                ", \nentType=" + entType.getName() +
                ", \nentName='" + entName + '\'' +
                ", \nparameters=" + parameters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EAVObject eavObject = (EAVObject) o;
        return id.equals(eavObject.id) && entType.equals(eavObject.entType) && Objects.equals(entName, eavObject.entName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entType, entName);
    }


}
