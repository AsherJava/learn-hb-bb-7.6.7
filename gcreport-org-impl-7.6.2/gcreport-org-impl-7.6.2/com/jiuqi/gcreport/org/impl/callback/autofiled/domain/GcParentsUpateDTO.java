/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.callback.autofiled.domain;

public class GcParentsUpateDTO {
    private String tableName;
    private String gcparents;
    private String id;

    public GcParentsUpateDTO() {
    }

    public GcParentsUpateDTO(String tableName, String gcparents, String id) {
        this.tableName = tableName;
        this.gcparents = gcparents;
        this.id = id;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getGcparents() {
        return this.gcparents;
    }

    public void setGcparents(String gcparents) {
        this.gcparents = gcparents;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return "GcParentsUpateDTO{tableName='" + this.tableName + '\'' + ", gcparents='" + this.gcparents + '\'' + ", id='" + this.id + '\'' + '}';
    }
}

