/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 */
package com.jiuqi.bde.plugin.gcreport.assist;

import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;

public class GcreportAssistPojo
extends BaseAcctAssist {
    private String valueType;
    private String tableName;

    public String getTableName() {
        return this.tableName;
    }

    public String getValueType() {
        return this.valueType;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

