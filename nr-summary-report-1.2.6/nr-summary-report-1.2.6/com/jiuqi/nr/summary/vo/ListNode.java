/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

public class ListNode {
    private String key;
    private String code;
    private String title;
    private Object data;

    public ListNode(String key, String code, String title) {
        this.key = key;
        this.code = code;
        this.title = title;
    }

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

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

