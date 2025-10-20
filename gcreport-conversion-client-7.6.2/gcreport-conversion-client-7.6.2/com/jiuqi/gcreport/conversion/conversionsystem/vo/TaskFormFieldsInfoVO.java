/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionsystem.vo;

public class TaskFormFieldsInfoVO {
    private String id;
    private String code;
    private String title;
    private String name;
    private String formId;
    private String formCode;
    private String showCode;
    private String tableName;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getShowCode() {
        this.showCode = this.formCode != null && this.code != null ? this.formCode + '[' + this.code + ']' : this.code;
        return this.showCode;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormId() {
        return this.formId;
    }
}

