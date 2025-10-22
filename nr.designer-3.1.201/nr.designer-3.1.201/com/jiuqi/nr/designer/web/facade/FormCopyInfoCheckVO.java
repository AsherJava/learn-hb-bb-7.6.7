/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import java.util.List;

public class FormCopyInfoCheckVO {
    private String srcFormSchemeKey;
    private List<String> srcFormGroupKeys;
    private List<String> srcFormKeys;
    private List<String> desFormSchemeKeys;
    private List<String> desDataSchemeKeys;

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public List<String> getSrcFormGroupKeys() {
        return this.srcFormGroupKeys;
    }

    public void setSrcFormGroupKeys(List<String> srcFormGroupKeys) {
        this.srcFormGroupKeys = srcFormGroupKeys;
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

    public List<String> getDesDataSchemeKeys() {
        return this.desDataSchemeKeys;
    }

    public void setDesDataSchemeKeys(List<String> desDataSchemeKeys) {
        this.desDataSchemeKeys = desDataSchemeKeys;
    }
}

