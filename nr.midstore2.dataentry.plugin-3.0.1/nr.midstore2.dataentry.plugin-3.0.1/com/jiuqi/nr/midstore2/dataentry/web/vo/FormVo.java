/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.dataentry.web.vo;

public class FormVo {
    private String groupKey;
    private String formKey;

    public FormVo() {
    }

    public FormVo(String formKey) {
        this.formKey = formKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

