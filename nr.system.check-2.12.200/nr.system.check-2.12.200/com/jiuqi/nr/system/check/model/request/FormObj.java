/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.model.request;

import java.util.List;

public class FormObj {
    private String formType;
    private List<String> selected;

    public String getFormType() {
        return this.formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public List<String> getSelected() {
        return this.selected;
    }

    public void setSelected(List<String> selected) {
        this.selected = selected;
    }
}

