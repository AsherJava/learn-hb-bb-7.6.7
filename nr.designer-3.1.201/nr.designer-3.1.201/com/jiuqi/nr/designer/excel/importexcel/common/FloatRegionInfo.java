/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.excel.importexcel.common;

public class FloatRegionInfo {
    private int regionTop;
    private int height;
    private int count;

    public FloatRegionInfo(int regionTop, int height, int count) {
        this.regionTop = regionTop;
        this.height = height;
        this.count = count;
    }

    public int getRegionTop() {
        return this.regionTop;
    }

    public void setRegionTop(int regionTop) {
        this.regionTop = regionTop;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

