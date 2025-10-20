/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

public class GcBusinessTypeCountVO {
    private String code;
    private String title;
    private int count;

    public GcBusinessTypeCountVO(String code, String title, int count) {
        this.code = code;
        this.title = title + "(" + count + ")";
        this.count = count;
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

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

