/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

import com.jiuqi.dc.base.common.enums.DataType;

public enum OptionType {
    BASEDATA(DataType.STRING, "basedata", "\u57fa\u7840\u6570\u636e", "", true),
    SELECT(DataType.STRING, "sequence", "\u4e0b\u62c9\u9009\u62e9", "select", true),
    SELECT_SINGLE(DataType.STRING, "sequence", "\u4e0b\u62c9\u5355\u9009", "select", false),
    CHECKBOX(DataType.STRING, "sequence", "\u590d\u9009\u6846", "checkbox", true),
    RADIO(DataType.STRING, "sequence", "\u5355\u9009", "radio", false),
    STRING(DataType.STRING, "string", "\u5b57\u7b26", "input"),
    TEXT(DataType.STRING, "string", "\u5b57\u7b26", "text"),
    INT(DataType.INT, "integer", "\u6574\u6570", ""),
    BOOLEAN(DataType.STRING, "bool", "\u5e03\u5c14", "checkbox"),
    BOOLEAN_SELECT(DataType.STRING, "bool", "\u5e03\u5c14", "select");

    private DataType dataType;
    private String type;
    private String name;
    private String showForm;
    private Boolean multipleFlag;
    private Boolean readonlyFlag = false;

    private OptionType(DataType dataType, String type, String name, String showForm) {
        this.dataType = dataType;
        this.type = type;
        this.name = name;
        this.showForm = showForm;
    }

    private OptionType(DataType dataType, String type, String name, String showForm, Boolean multipleFlag) {
        this.dataType = dataType;
        this.type = type;
        this.name = name;
        this.showForm = showForm;
        this.multipleFlag = multipleFlag;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getShowForm() {
        return this.showForm;
    }

    public Boolean getMultipleFlag() {
        return this.multipleFlag;
    }

    public Boolean getReadonlyFlag() {
        return this.readonlyFlag;
    }

    public void setReadonlyFlag(Boolean readonlyFlag) {
        this.readonlyFlag = readonlyFlag;
    }
}

