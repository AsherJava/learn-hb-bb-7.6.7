/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.bean;

import java.io.Serializable;

public class PlanTaskFormParam
implements Serializable {
    private String groupKey;
    private String formKey;

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

