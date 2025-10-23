/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.report;

import java.io.Serializable;

public class SummaryFloatRegion
implements Serializable {
    private int position;
    private String tableName;

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

