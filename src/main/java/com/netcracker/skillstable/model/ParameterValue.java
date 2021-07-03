package com.netcracker.skillstable.model;

public class ParameterValue {
    private Long valueInt;
    private String valueStr;


    public ParameterValue() {
    }

    public ParameterValue(Long valueInt, String valueStr) {
        this.valueInt = valueInt;
        this.valueStr = valueStr;
    }


    public Long getValueInt() {
        return valueInt;
    }

    public void setValueInt(Long valueInt) {
        this.valueInt = valueInt;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }
}
