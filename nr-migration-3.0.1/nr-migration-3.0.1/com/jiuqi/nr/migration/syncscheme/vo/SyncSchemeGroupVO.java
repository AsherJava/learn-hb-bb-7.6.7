/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.syncscheme.vo;

import com.jiuqi.nr.migration.syncscheme.bean.SyncSchemeGroup;

public class SyncSchemeGroupVO {
    private String key;
    private String title;
    private String parent;

    public SyncSchemeGroupVO(SyncSchemeGroup group) {
        this.key = group.getKey();
        this.title = group.getTitle();
        this.parent = group.getParent();
    }

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

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}

