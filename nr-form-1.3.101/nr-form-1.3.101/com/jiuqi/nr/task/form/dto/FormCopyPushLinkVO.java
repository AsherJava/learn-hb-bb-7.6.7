/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import java.util.List;

public class FormCopyPushLinkVO {
    private String srcFormSchemeKey;
    private List<String> desFormSchemeKeys;

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public List<String> getDesFormSchemeKeys() {
        return this.desFormSchemeKeys;
    }

    public void setDesFormSchemeKeys(List<String> desFormSchemeKeys) {
        this.desFormSchemeKeys = desFormSchemeKeys;
    }
}

