/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 */
package com.jiuqi.bde.plugin.k3_cloud.util;

import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;

public class AssistPojo
extends BaseAcctAssist {
    private String assField;
    private String pkField;
    private String codeField;
    private String nameField;
    private String tableName;

    public String getAssField() {
        return this.assField;
    }

    public void setAssField(String assField) {
        this.assField = assField;
    }

    public String getPkField() {
        return this.pkField;
    }

    public void setPkField(String pkField) {
        this.pkField = pkField;
    }

    public String getCodeField() {
        return this.codeField;
    }

    public void setCodeField(String codeField) {
        this.codeField = codeField;
    }

    public String getNameField() {
        return this.nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

