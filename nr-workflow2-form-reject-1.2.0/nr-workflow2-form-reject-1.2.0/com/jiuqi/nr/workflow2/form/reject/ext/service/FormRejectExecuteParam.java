/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.form.reject.ext.service;

import java.util.List;

public class FormRejectExecuteParam {
    private String formId;
    private String comment;
    private List<String> rejectFormIds;

    public FormRejectExecuteParam() {
    }

    public FormRejectExecuteParam(String formId) {
        this.formId = formId;
    }

    public FormRejectExecuteParam(List<String> rejectFormIds) {
        this.rejectFormIds = rejectFormIds;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public List<String> getRejectFormIds() {
        return this.rejectFormIds;
    }

    public void setRejectFormIds(List<String> rejectFormIds) {
        this.rejectFormIds = rejectFormIds;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

