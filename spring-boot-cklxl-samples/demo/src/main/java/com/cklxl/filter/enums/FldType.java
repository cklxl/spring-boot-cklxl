package com.cklxl.filter.enums;

public enum FldType {

    SINGLE("01", "条件"), GROUP("02", "组合条件");

    private final String value;

    private final String label;

    FldType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

}
