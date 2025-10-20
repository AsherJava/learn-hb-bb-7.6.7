/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.enumerate;

public enum ParamTypeEnum {
    STRING("string", "string_data"),
    NUMBER("num", "number_data"),
    INTEGER("integer", "int_data"),
    BOOL("bool", "boolean_data"),
    SEQUENCE("sequence", "string_data"),
    DATE("date", "date_data"),
    DATE_TIME("date_time", "date_time_data"),
    UUID("uuid");

    private String typeName;
    private String tempTableField;

    private ParamTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    private ParamTypeEnum(String typeName, String tempTableField) {
        this.typeName = typeName;
        this.tempTableField = tempTableField;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public String getTempTableField() {
        return this.tempTableField;
    }

    public static ParamTypeEnum val(String typeName) {
        for (ParamTypeEnum paramType : ParamTypeEnum.values()) {
            if (!paramType.getTypeName().equalsIgnoreCase(typeName)) continue;
            return paramType;
        }
        return null;
    }
}

