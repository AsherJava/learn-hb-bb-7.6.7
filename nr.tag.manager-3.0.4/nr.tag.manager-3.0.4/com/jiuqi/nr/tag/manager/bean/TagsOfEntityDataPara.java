/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.bean;

import java.util.List;

public class TagsOfEntityDataPara {
    private String viewKey;
    private List<String> entityDataKeys;

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public List<String> getEntityDataKeys() {
        return this.entityDataKeys;
    }

    public void setEntityDataKeys(List<String> entityDataKeys) {
        this.entityDataKeys = entityDataKeys;
    }
}

