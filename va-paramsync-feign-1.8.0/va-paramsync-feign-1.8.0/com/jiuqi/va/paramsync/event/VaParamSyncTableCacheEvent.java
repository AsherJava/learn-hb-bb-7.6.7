/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.event;

import org.springframework.context.ApplicationEvent;

public class VaParamSyncTableCacheEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private String tableName;

    public VaParamSyncTableCacheEvent(Object source) {
        super(source);
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

