/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.log.impl.domain;

public class SqlInfoLogDO {
    private String id;
    private String sqlFullText;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSqlFullText() {
        return this.sqlFullText;
    }

    public void setSqlFullText(String sqlFullText) {
        this.sqlFullText = sqlFullText;
    }
}

