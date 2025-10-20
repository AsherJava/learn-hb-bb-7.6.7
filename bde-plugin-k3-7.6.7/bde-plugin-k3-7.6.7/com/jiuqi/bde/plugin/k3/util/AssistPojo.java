/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 */
package com.jiuqi.bde.plugin.k3.util;

import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;

public class AssistPojo
extends BaseAcctAssist {
    private String tableName;
    private Integer assId;

    public Integer getAssId() {
        return this.assId;
    }

    public void setAssId(Integer assId) {
        this.assId = assId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

