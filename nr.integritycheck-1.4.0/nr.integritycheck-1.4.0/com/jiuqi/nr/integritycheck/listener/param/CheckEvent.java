/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.listener.param;

public class CheckEvent {
    private String id;
    private String tableName;

    public CheckEvent(String id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }

    public String getId() {
        return this.id;
    }

    public String getTableName() {
        return this.tableName;
    }
}

