/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.entity;

public class SearchDataFieldDO {
    private String key;
    private String code;
    private String title;
    private String groupKey;

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

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String toString() {
        return "SearchDataFieldDO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", groupKey='" + this.groupKey + '\'' + '}';
    }
}

