/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.syncscheme.bean;

import java.util.Objects;

public class SyncScheme {
    private String key;
    private String code;
    private String title;
    private String group;
    private String data;
    private long updateTime;
    private String order;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
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
        SyncScheme that = (SyncScheme)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return Objects.hash(this.key);
    }

    public boolean equalTest(SyncScheme scheme) {
        return this.key.equals(scheme.getKey()) && this.code.equals(scheme.getCode()) && this.title.equals(scheme.getTitle()) && (this.group == null && scheme.getGroup() == null || this.group.equals(scheme.getGroup())) && (this.data == null && scheme.getData() == null || new String(this.data).equals(new String(scheme.getData()))) && this.order.equals(scheme.getOrder());
    }
}

