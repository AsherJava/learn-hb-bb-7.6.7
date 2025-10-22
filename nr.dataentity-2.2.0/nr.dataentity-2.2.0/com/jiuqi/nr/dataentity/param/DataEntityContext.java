/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity.param;

public class DataEntityContext {
    private boolean queryByKey;
    private boolean sorted;
    private boolean queryDim;
    private String taskKey;
    private String formSchemeKey;
    private String formKey;

    public boolean isQueryByKey() {
        return this.queryByKey;
    }

    public void setQueryByKey(boolean queryByKey) {
        this.queryByKey = queryByKey;
    }

    public boolean isSorted() {
        return this.sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public boolean isQueryDim() {
        return this.queryDim;
    }

    public void setQueryDim(boolean queryDim) {
        this.queryDim = queryDim;
    }

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
}

