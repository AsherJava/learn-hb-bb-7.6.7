/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.vo;

public class EffectTableVO {
    private String tableName;
    private String title;

    public EffectTableVO() {
    }

    public EffectTableVO(String tableName, String title) {
        this.tableName = tableName;
        this.title = title;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

