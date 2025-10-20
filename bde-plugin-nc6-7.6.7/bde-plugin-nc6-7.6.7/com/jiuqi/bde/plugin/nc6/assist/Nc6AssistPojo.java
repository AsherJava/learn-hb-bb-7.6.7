/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 */
package com.jiuqi.bde.plugin.nc6.assist;

import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;

public class Nc6AssistPojo
extends BaseAcctAssist {
    private String tableName;
    private String tablePk;
    private String docFreeTableName;
    private int docFreeTableNum;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTablePk() {
        return this.tablePk;
    }

    public void setTablePk(String tablePk) {
        this.tablePk = tablePk;
    }

    public String getDocFreeTableName() {
        return this.docFreeTableName;
    }

    public void setDocFreeTableName(String docFreeTableName) {
        this.docFreeTableName = docFreeTableName;
    }

    public int getDocFreeTableNum() {
        return this.docFreeTableNum;
    }

    public void setDocFreeTableNum(int docFreeTableNum) {
        this.docFreeTableNum = docFreeTableNum;
    }
}

