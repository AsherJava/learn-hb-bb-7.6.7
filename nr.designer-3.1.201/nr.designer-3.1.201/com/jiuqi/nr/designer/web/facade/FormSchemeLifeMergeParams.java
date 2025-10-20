/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.FormSchemeLifeObj;
import java.util.List;

public class FormSchemeLifeMergeParams {
    private String taskKey;
    private String formSchemeKey;
    List<FormSchemeLifeObj> formSchemeLifeObjs;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<FormSchemeLifeObj> getFormSchemeLifeObjs() {
        return this.formSchemeLifeObjs;
    }

    public void setFormSchemeLifeObjs(List<FormSchemeLifeObj> formSchemeLifeObjs) {
        this.formSchemeLifeObjs = formSchemeLifeObjs;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

