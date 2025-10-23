/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.api.response;

import java.util.List;

public class BatchLockUnitFormsInfo {
    private String unitTitle;
    private List<String> formTitle;

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public List<String> getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(List<String> formTitle) {
        this.formTitle = formTitle;
    }
}

