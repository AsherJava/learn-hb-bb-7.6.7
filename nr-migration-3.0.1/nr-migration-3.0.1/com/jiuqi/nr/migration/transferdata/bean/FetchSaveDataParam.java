/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

import com.jiuqi.nr.migration.transferdata.bean.DimInfo;
import java.util.List;

public class FetchSaveDataParam {
    private String taskKey;
    private String formSchemeKey;
    private String formKey;
    private List<DimInfo> dimInfos;

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

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public List<DimInfo> getDimInfos() {
        return this.dimInfos;
    }

    public void setDimInfos(List<DimInfo> dimInfos) {
        this.dimInfos = dimInfos;
    }
}

