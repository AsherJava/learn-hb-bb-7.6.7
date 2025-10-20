/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 */
package com.jiuqi.bde.plugin.eas8.util;

import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;

public class AssistPojo
extends BaseAcctAssist {
    private String groupId;
    private String tableName;

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String fGroupId) {
        this.groupId = fGroupId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

