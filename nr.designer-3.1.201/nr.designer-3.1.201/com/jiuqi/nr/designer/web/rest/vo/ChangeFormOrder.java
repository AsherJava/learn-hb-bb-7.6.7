/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class ChangeFormOrder {
    private String formKey;
    private String groupKey;
    private String order;

    public ChangeFormOrder() {
    }

    public ChangeFormOrder(String formKey, String groupKey, String order) {
        this.formKey = formKey;
        this.groupKey = groupKey;
        this.order = order;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

