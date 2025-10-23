/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulaeditor.vo;

public class ParameterData {
    private String name;
    private String title;
    private String dataType;

    public ParameterData() {
    }

    public ParameterData(String name, String title, String dataType, boolean canOmit) {
        this.name = name;
        this.title = title;
        this.dataType = dataType;
    }

    public ParameterData(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}

