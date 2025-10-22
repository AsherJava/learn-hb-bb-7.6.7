/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import java.util.List;

public class FormCopyInfoParams {
    private String desFormSchemeKey;
    private List<String> srcFormKeys;

    public String getDesFormSchemeKey() {
        return this.desFormSchemeKey;
    }

    public void setDesFormSchemeKey(String desFormSchemeKey) {
        this.desFormSchemeKey = desFormSchemeKey;
    }

    public List<String> getSrcFormKeys() {
        return this.srcFormKeys;
    }

    public void setSrcFormKeys(List<String> srcFormKeys) {
        this.srcFormKeys = srcFormKeys;
    }
}

