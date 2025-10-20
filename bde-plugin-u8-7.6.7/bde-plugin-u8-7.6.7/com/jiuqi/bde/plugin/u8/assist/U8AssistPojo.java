/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 */
package com.jiuqi.bde.plugin.u8.assist;

import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;

public class U8AssistPojo
extends BaseAcctAssist {
    private String odsTableName;
    private String tablePk;
    private String nameField;
    private String assField;

    public String getTablePk() {
        return this.tablePk;
    }

    public void setTablePk(String tablePk) {
        this.tablePk = tablePk;
    }

    public String getNameField() {
        return this.nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public String getOdsTableName() {
        return this.odsTableName;
    }

    public void setOdsTableName(String odsTableName) {
        this.odsTableName = odsTableName;
    }

    public String getAssField() {
        return this.assField;
    }

    public void setAssField(String assField) {
        this.assField = assField;
    }
}

