/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 */
package com.jiuqi.bi.dataset.report.remote.controller.vo;

import com.jiuqi.np.definition.common.FieldType;

public class SelectFieldVo {
    private String code;
    private String fieldTitle;
    private String fieldCode;
    private FieldType dataType;
    private String tableName;
    private String tableKey;
    private String taskId;
    private String formSchemeId;
    private String formkey;
    private String expression;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public FieldType getDataType() {
        return this.dataType;
    }

    public void setDataType(FieldType dataType) {
        this.dataType = dataType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getFormkey() {
        return this.formkey;
    }

    public void setFormkey(String formkey) {
        this.formkey = formkey;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

