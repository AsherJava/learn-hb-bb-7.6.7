/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class RequestAllFormStyle {
    private String key;
    private int type;

    public RequestAllFormStyle() {
    }

    public RequestAllFormStyle(String key, int type) {
        this.key = key;
        this.type = type;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

