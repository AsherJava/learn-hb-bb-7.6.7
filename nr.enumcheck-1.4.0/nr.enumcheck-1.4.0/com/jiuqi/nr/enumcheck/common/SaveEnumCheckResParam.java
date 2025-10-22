/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import com.jiuqi.nr.enumcheck.common.EnumDataCheckResultItem;
import java.util.List;

public class SaveEnumCheckResParam {
    private String asyncTaskId;
    private String taskKey;
    private String period;
    private String formSchemeKey;
    private List<EnumDataCheckResultItem> enumDataCheckResultItems;

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<EnumDataCheckResultItem> getEnumDataCheckResultItems() {
        return this.enumDataCheckResultItems;
    }

    public void setEnumDataCheckResultItems(List<EnumDataCheckResultItem> enumDataCheckResultItems) {
        this.enumDataCheckResultItems = enumDataCheckResultItems;
    }
}

