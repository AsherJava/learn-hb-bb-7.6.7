/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 */
package com.jiuqi.bde.plugin.bip_flagship.util;

import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;

public class AssistPojo
extends BaseAcctAssist {
    private Integer docNum;
    private String tableName;
    private String tableId;
    private String codeField;
    private String nameField;
    private String pkField;
    private String mainTableName;
    private String mainTableId;
    private String assCode;

    public Integer getDocNum() {
        return this.docNum;
    }

    public void setDocNum(Integer docNum) {
        this.docNum = docNum;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableId() {
        return this.tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
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

    public String getPkField() {
        return this.pkField;
    }

    public void setPkField(String pkField) {
        this.pkField = pkField;
    }

    public String getMainTableName() {
        return this.mainTableName;
    }

    public void setMainTableName(String mainTableName) {
        this.mainTableName = mainTableName;
    }

    public String getMainTableId() {
        return this.mainTableId;
    }

    public void setMainTableId(String mainTableId) {
        this.mainTableId = mainTableId;
    }

    public String getAssCode() {
        return this.assCode;
    }

    public void setAssCode(String assCode) {
        this.assCode = assCode;
    }
}

