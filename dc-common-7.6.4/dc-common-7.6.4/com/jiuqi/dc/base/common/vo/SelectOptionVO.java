/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.vo;

public class SelectOptionVO {
    private String code;
    private String name;

    public SelectOptionVO() {
    }

    public SelectOptionVO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

