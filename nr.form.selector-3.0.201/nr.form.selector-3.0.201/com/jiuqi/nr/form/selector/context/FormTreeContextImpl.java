/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.selector.context;

import com.jiuqi.nr.form.selector.context.FormQueryContextImpl;
import java.util.List;

public class FormTreeContextImpl
extends FormQueryContextImpl {
    private List<String> checkers;
    private List<String> checkForms;
    private String formSourceId;

    public List<String> getCheckers() {
        return this.checkers;
    }

    public void setCheckers(List<String> checkers) {
        this.checkers = checkers;
    }

    public List<String> getCheckForms() {
        return this.checkForms;
    }

    public void setCheckForms(List<String> checkForms) {
        this.checkForms = checkForms;
    }

    public String getFormSourceId() {
        return this.formSourceId;
    }

    public void setFormSourceId(String formSourceId) {
        this.formSourceId = formSourceId;
    }
}

