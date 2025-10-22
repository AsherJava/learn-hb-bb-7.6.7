/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.bean;

import java.util.List;

public class TagsOfEntityDataModify {
    private String entKey;
    private String viewKey;
    private List<String> tagKeys;

    public String getEntKey() {
        return this.entKey;
    }

    public void setEntKey(String entKey) {
        this.entKey = entKey;
    }

    public List<String> getTagKeys() {
        return this.tagKeys;
    }

    public void setTagKeys(List<String> tagKeys) {
        this.tagKeys = tagKeys;
    }

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }
}

