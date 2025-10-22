/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulapenetration.defines;

import java.util.List;
import java.util.Map;

public class BatchEnableParam {
    private List<Map<String, String>> dimsList;
    private String formSchemeKey;
    private List<String> linkKeys;

    public List<Map<String, String>> getDimsList() {
        return this.dimsList;
    }

    public void setDimsList(List<Map<String, String>> dimsList) {
        this.dimsList = dimsList;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getLinkKeys() {
        return this.linkKeys;
    }

    public void setLinkKeys(List<String> linkKeys) {
        this.linkKeys = linkKeys;
    }
}

