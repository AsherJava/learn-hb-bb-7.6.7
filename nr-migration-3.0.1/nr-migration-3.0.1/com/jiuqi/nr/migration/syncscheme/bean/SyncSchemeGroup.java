/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.syncscheme.bean;

import java.util.Objects;

public class SyncSchemeGroup {
    private String key;
    private String title;
    private String parent;
    private long updateTime;
    private String order;

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

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        SyncSchemeGroup group = (SyncSchemeGroup)o;
        return this.key.equals(group.getKey());
    }

    public int hashCode() {
        return Objects.hash(this.key);
    }

    public boolean equalTest(SyncSchemeGroup group) {
        return this.key.equals(group.getKey()) && this.title.equals(group.getTitle()) && (this.parent == null && group.getParent() == null || this.parent.equals(group.getParent())) && this.order.equals(group.getOrder());
    }
}

