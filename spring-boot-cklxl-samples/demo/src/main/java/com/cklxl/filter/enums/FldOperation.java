package com.cklxl.filter.enums;

public enum FldOperation {
    EQ("=", "等于"),
    NE("!=", "不等于"),
    GT(">", "大于"),
    GE(">=", "大于等于"),
    LT("<", "小于"),
    LE("<=", "小于等于"),
    NULL("is null", "为空"),
    NOT_NULL("is not null", "不为空"),
    IN("in", "属于"),
    NOT_IN("not in", "不属于"),
    BETWEEN("between and", "介于"),
    NOT_BETWEEN("not between and", "不介于"),
    LIKE("like", "包含字符"),
    LIKE_RIGHT("like", "以...开头"),
    LIKE_LEFT("like", "以...结尾"),
    NOT_LIKE("not like", "不包含字符"),
    ;

    private final String value;
    private final String label;

    FldOperation(String value, String label) {
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
