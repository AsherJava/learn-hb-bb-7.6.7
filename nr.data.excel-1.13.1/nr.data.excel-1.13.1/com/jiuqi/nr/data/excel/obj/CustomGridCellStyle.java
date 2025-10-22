/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.obj;

import java.io.Serializable;

public class CustomGridCellStyle
implements Serializable {
    private static final long serialVersionUID = -7877837103530563339L;
    private int backGroundColor;
    private int backGroundStyle;

    public int getBackGroundColor() {
        return this.backGroundColor;
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public int getBackGroundStyle() {
        return this.backGroundStyle;
    }

    public void setBackGroundStyle(int backGroundStyle) {
        this.backGroundStyle = backGroundStyle;
    }
}

