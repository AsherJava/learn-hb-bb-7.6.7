/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.summary.define;

public class SumBaseZB {
    private String guid;
    private String name;
    private String title;
    private int dataType;
    private String tableName;
    private String relateCatalogId;
    private int lenth;
    private int decimal;
    private String measurement;
    private int applyType;

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRelateCatalogId() {
        return this.relateCatalogId;
    }

    public void setRelateCatalogId(String relateCatalogId) {
        this.relateCatalogId = relateCatalogId;
    }

    public int getLenth() {
        return this.lenth;
    }

    public void setLenth(int lenth) {
        this.lenth = lenth;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public String getMeasurement() {
        return this.measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public int getApplyType() {
        return this.applyType;
    }

    public void setApplyType(int applyType) {
        this.applyType = applyType;
    }

    public boolean equals(Object obj) {
        return this.guid.equals(((SumBaseZB)obj).getGuid());
    }
}

