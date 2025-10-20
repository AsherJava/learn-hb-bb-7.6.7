/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 */
package com.jiuqi.bde.plugin.gs_cloud.util;

import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;

public class AssistPojo
extends BaseAcctAssist {
    private String id;
    private String odsTableName;
    private String tablePK;
    private String assField;
    private String assSql;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOdsTableName() {
        return this.odsTableName;
    }

    public void setOdsTableName(String odsTableName) {
        this.odsTableName = odsTableName;
    }

    public String getTablePK() {
        return this.tablePK;
    }

    public void setTablePK(String tablePK) {
        this.tablePK = tablePK;
    }

    public String getAssField() {
        return this.assField;
    }

    public void setAssField(String assField) {
        this.assField = assField;
    }

    public String getAssSql() {
        return this.assSql;
    }

    public void setAssSql(String assSql) {
        this.assSql = assSql;
    }
}

