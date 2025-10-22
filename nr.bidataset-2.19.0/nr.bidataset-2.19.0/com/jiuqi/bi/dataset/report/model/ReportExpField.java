/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.FieldType
 */
package com.jiuqi.bi.dataset.report.model;

import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.report.model.ReportFieldType;

public class ReportExpField {
    private String code;
    private String title;
    private String zbTitle;
    private String exp;
    private FieldType fieldType;
    private int dataType;
    private String messageAlias;
    private String order;
    private ReportFieldType reportFieldType;
    private String keyField;

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getZbTitle() {
        return this.zbTitle;
    }

    public String getExp() {
        return this.exp;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public int getDataType() {
        return this.dataType;
    }

    public String getMessageAlias() {
        return this.messageAlias;
    }

    public String getOrder() {
        return this.order;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setZbTitle(String zbTitle) {
        this.zbTitle = zbTitle;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public void setMessageAlias(String messageAlias) {
        this.messageAlias = messageAlias;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public ReportFieldType getReportFieldType() {
        return this.reportFieldType;
    }

    public String getKeyField() {
        return this.keyField;
    }

    public void setReportFieldType(ReportFieldType reportFieldType) {
        this.reportFieldType = reportFieldType;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }
}

