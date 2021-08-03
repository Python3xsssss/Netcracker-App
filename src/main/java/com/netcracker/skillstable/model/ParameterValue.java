package com.netcracker.skillstable.model;

import java.util.Objects;

// old
public class ParameterValue {
    private Integer valueInt;
    private String valueStr;


    public ParameterValue() {
    }

    public ParameterValue(Integer valueInt, String valueStr) {
        this.valueInt = valueInt;
        this.valueStr = valueStr;
    }


    public Integer getValueInt() {
        return valueInt;
    }

    public void setValueInt(Integer valueInt) {
        this.valueInt = valueInt;
    }

    public String getValueTxt() {
        return valueStr;
    }

    public void setValueTxt(String valueStr) {
        this.valueStr = valueStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterValue that = (ParameterValue) o;
        return Objects.equals(valueInt, that.valueInt) && Objects.equals(valueStr, that.valueStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valueInt, valueStr);
    }
}
