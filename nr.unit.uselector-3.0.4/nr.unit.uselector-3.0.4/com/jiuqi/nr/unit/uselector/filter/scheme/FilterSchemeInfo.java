/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.unit.treestore.uselector.bean.USFilterScheme
 */
package com.jiuqi.nr.unit.uselector.filter.scheme;

import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterScheme;

public class FilterSchemeInfo {
    private String key;
    private String title;
    private boolean shared;
    private boolean canEdit;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShared() {
        return this.shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean isCanEdit() {
        return this.canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public static FilterSchemeInfo assign(USFilterScheme scheme) {
        FilterSchemeInfo schemeInfo = new FilterSchemeInfo();
        schemeInfo.setKey(scheme.getKey());
        schemeInfo.setTitle(scheme.getTitle());
        schemeInfo.setShared(scheme.isShared());
        return schemeInfo;
    }
}

