/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 */
package com.jiuqi.bde.plugin.nbrj_n9.util;

import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;

public class AssistPojo
extends BaseAcctAssist {
    private String assistField;
    private String tableName;
    private String tablePk;
    private String advancedSql;

    public String getAssistField() {
        return this.assistField;
    }

    public void setAssistField(String assistField) {
        this.assistField = assistField;
    }

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

    public String getAdvancedSql() {
        return this.advancedSql;
    }

    public void setAdvancedSql(String advancedSql) {
        this.advancedSql = advancedSql;
    }
}

