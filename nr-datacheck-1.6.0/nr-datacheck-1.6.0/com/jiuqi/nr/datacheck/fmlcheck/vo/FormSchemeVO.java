/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.datacheck.fmlcheck.vo;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;

public class FormSchemeVO {
    private String formSchemeKey;
    private String taskKey;

    public FormSchemeVO(FormSchemeDefine formSchemeDefine) {
        this.formSchemeKey = formSchemeDefine.getKey();
        this.taskKey = formSchemeDefine.getTaskKey();
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

