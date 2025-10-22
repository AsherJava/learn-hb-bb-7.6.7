/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlrule;

public class ColumnVO {
    private String code;
    private String title;

    public ColumnVO(String code, String title) {
        this.code = code;
        this.title = title;
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

    public String toString() {
        return "ColumnVO{code='" + this.code + '\'' + ", title='" + this.title + '\'' + '}';
    }
}

