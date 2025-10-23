/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.param;

import java.util.List;

public class ReleaseFormParam {
    private String formSchemeKey;
    private List<String> formKeys;
    private boolean publishDataTable;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public boolean isPublishDataTable() {
        return this.publishDataTable;
    }

    public void setPublishDataTable(boolean publishDataTable) {
        this.publishDataTable = publishDataTable;
    }
}

