/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.obj;

import java.util.List;

public class AppendedFileDownloadParam {
    private String dataSchemeKey;
    private String formSchemeKey;
    private List<String> fileGroupKeys;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getFileGroupKeys() {
        return this.fileGroupKeys;
    }

    public void setFileGroupKeys(List<String> fileGroupKeys) {
        this.fileGroupKeys = fileGroupKeys;
    }
}

