/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequery.multcheck.vo;

import com.jiuqi.nr.singlequery.multcheck.vo.QueryCheckModel;
import java.util.List;

public class QueryCheckParam {
    String taskKey;
    String formSchemeKey;
    List<QueryCheckModel> models;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<QueryCheckModel> getModels() {
        return this.models;
    }

    public void setModels(List<QueryCheckModel> models) {
        this.models = models;
    }
}

