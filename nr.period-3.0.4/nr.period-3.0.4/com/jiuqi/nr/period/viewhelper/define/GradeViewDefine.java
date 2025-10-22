/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.viewhelper.define;

public class GradeViewDefine {
    private String key;
    private String entityKey;
    private String tableKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public GradeViewDefine(String key, String entityKey, String tableKey) {
        this.key = key;
        this.entityKey = entityKey;
        this.tableKey = tableKey;
    }

    public GradeViewDefine() {
    }
}

