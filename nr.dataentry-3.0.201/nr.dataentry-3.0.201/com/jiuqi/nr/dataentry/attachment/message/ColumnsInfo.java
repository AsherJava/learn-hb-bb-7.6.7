/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.message;

public class ColumnsInfo {
    private String key;
    private String title;

    public ColumnsInfo() {
    }

    public ColumnsInfo(String key, String title) {
        this.key = key;
        this.title = title;
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
}

