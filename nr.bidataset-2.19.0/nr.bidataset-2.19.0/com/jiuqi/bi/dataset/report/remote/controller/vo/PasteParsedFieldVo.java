/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.remote.controller.vo;

import com.jiuqi.bi.dataset.report.model.ReportExpField;

public class PasteParsedFieldVo
extends ReportExpField {
    private String errorExpMsg;

    public PasteParsedFieldVo(ReportExpField field) {
        this.setCode(field.getCode());
        this.setTitle(field.getTitle());
        this.setZbTitle(field.getZbTitle());
        this.setExp(field.getExp());
        this.setFieldType(field.getFieldType());
        this.setDataType(field.getDataType());
        this.setMessageAlias(field.getMessageAlias());
        this.setOrder(field.getOrder());
        this.setReportFieldType(field.getReportFieldType());
        this.setKeyField(field.getKeyField());
    }

    public String getErrorExpMsg() {
        return this.errorExpMsg;
    }

    public void setErrorExpMsg(String errorExpMsg) {
        this.errorExpMsg = errorExpMsg;
    }
}

