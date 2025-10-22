/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.extend.param;

import com.jiuqi.nr.data.logic.internal.obj.FmlDimForm;
import java.util.List;

public class FmlDimFormExtParam {
    private String taskKey;
    private String formSchemeKey;
    private List<FmlDimForm> fmlDimForms;

    public FmlDimFormExtParam() {
    }

    public FmlDimFormExtParam(String taskKey, String formSchemeKey, List<FmlDimForm> fmlDimForms) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.fmlDimForms = fmlDimForms;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<FmlDimForm> getFmlDimForms() {
        return this.fmlDimForms;
    }

    public void setFmlDimForms(List<FmlDimForm> fmlDimForms) {
        this.fmlDimForms = fmlDimForms;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

