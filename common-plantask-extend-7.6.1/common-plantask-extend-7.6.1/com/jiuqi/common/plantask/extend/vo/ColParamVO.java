/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.plantask.extend.vo;

public class ColParamVO {
    private String name;
    private String title;
    private String paramType;
    private String showForm;
    private Boolean vertical;
    private Boolean multipleFlag;
    private Boolean mustInput;
    private String baseDataTable;
    private String source;
    private String defaultValue;
    private Integer maxInt;
    private Integer minInt;
    private Integer rowIndex;
    private Integer colIndex;
    private Integer colSpan;
    private Integer rowSpan;

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

    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getShowForm() {
        return this.showForm;
    }

    public void setShowForm(String showForm) {
        this.showForm = showForm;
    }

    public Boolean getMultipleFlag() {
        return this.multipleFlag;
    }

    public void setMultipleFlag(Boolean multipleFlag) {
        this.multipleFlag = multipleFlag;
    }

    public Boolean getMustInput() {
        return this.mustInput;
    }

    public void setMustInput(Boolean mustInput) {
        this.mustInput = mustInput;
    }

    public String getBaseDataTable() {
        return this.baseDataTable;
    }

    public void setBaseDataTable(String baseDataTable) {
        this.baseDataTable = baseDataTable;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getMaxInt() {
        return this.maxInt;
    }

    public void setMaxInt(Integer maxInt) {
        this.maxInt = maxInt;
    }

    public Integer getMinInt() {
        return this.minInt;
    }

    public void setMinInt(Integer minInt) {
        this.minInt = minInt;
    }

    public Integer getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Integer getColIndex() {
        return this.colIndex;
    }

    public void setColIndex(Integer colIndex) {
        this.colIndex = colIndex;
    }

    public Integer getColSpan() {
        return this.colSpan;
    }

    public void setColSpan(Integer colSpan) {
        this.colSpan = colSpan;
    }

    public Integer getRowSpan() {
        return this.rowSpan == null ? 1 : this.rowSpan;
    }

    public void setRowSpan(Integer rowSpan) {
        this.rowSpan = rowSpan;
    }

    public Boolean getVertical() {
        return this.vertical;
    }

    public void setVertical(Boolean vertical) {
        this.vertical = vertical;
    }

    public String toString() {
        return "ColParamVO{name='" + this.name + '\'' + ", title='" + this.title + '\'' + ", paramType='" + this.paramType + '\'' + ", showForm='" + this.showForm + '\'' + ", vertical=" + this.vertical + ", multipleFlag=" + this.multipleFlag + ", mustInput=" + this.mustInput + ", baseDataTable='" + this.baseDataTable + '\'' + ", source='" + this.source + '\'' + ", defaultValue='" + this.defaultValue + '\'' + ", maxInt=" + this.maxInt + ", minInt=" + this.minInt + ", rowIndex=" + this.rowIndex + ", colIndex=" + this.colIndex + ", colSpan=" + this.colSpan + ", rowSpan=" + this.rowSpan + '}';
    }
}

