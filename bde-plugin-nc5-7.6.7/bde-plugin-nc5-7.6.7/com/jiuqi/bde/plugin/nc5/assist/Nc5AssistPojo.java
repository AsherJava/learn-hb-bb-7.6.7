/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 */
package com.jiuqi.bde.plugin.nc5.assist;

import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;

public class Nc5AssistPojo
extends BaseAcctAssist {
    private String tableName;
    private String baseDocTableName;
    private String tablePk;
    private String codeField;
    private String nameField;
    private String define;

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

    public String getDefine() {
        return this.define;
    }

    public void setDefine(String define) {
        this.define = define;
    }

    public String getBaseDocTableName() {
        return this.baseDocTableName;
    }

    public void setBaseDocTableName(String baseDocTableName) {
        this.baseDocTableName = baseDocTableName;
    }
}

