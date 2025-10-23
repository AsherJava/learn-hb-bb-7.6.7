/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import java.util.List;

public class FormCopyInfoCheckVO {
    private String srcFormSchemeKey;
    private List<String> srcFormKeys;
    private List<String> desFormSchemeKeys;

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public List<String> getSrcFormKeys() {
        return this.srcFormKeys;
    }

    public void setSrcFormKeys(List<String> srcFormKeys) {
        this.srcFormKeys = srcFormKeys;
    }

    public List<String> getDesFormSchemeKeys() {
        return this.desFormSchemeKeys;
    }

    public void setDesFormSchemeKeys(List<String> desFormSchemeKeys) {
        this.desFormSchemeKeys = desFormSchemeKeys;
    }
}

