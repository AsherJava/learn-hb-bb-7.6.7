/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import com.jiuqi.np.grid2.AbstractColRowInfo;
import java.io.Serializable;

public class ColumnInfo
extends AbstractColRowInfo
implements Serializable {
    private static final long serialVersionUID = -6312682821141997041L;
    boolean grab = false;

    public ColumnInfo() {
        this.size = 100;
    }

    public ColumnInfo(ColumnInfo info) {
        this();
        this.setSize(info.getSize());
        this.setAutoSize(info.isAutoSize());
        this.setVisiable(info.isVisiable());
        this.setColumnGrab(info.grab);
    }

    public int getColWidth() {
        return this.getSize();
    }

    public void setColWidth(int width) {
        this.setSize(width);
    }

    public void setColAutoWidth(boolean value) {
        this.setAutoSize(value);
    }

    public boolean isColAutoWidth() {
        return this.isAutoSize();
    }

    public boolean isColumnHidden() {
        return !this.isVisiable();
    }

    public void setColumnHidden(boolean value) {
        this.setVisiable(!value);
    }

    public void setColumnGrab(boolean value) {
        this.grab = value;
    }

    public boolean getColumnGrab() {
        return this.grab;
    }
}

