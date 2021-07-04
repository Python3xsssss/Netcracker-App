package com.netcracker.skillstable.model;

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

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }
}
