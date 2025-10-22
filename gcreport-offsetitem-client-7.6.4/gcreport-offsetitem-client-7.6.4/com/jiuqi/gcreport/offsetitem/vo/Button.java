/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

public class Button {
    private String value;
    private String showType;
    private String isShow;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getIsShow() {
        return this.isShow;
    }

    public void setIsShow(String show) {
        this.isShow = show;
    }

    public String toString() {
        return "Button{value='" + this.value + '\'' + ", showType='" + this.showType + '\'' + ", isShow='" + this.isShow + '\'' + '}';
    }
}

