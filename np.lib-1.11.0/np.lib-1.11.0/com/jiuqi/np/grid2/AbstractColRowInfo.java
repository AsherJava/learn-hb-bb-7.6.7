/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import java.io.Serializable;

public abstract class AbstractColRowInfo
implements Serializable {
    private static final long serialVersionUID = 3652234322280096308L;
    int size;
    boolean autoSize = false;
    boolean visiable = true;

    protected int getSize() {
        return this.size;
    }

    protected void setSize(int size) {
        this.size = size;
    }

    protected boolean isAutoSize() {
        return this.autoSize;
    }

    protected void setAutoSize(boolean autoSize) {
        this.autoSize = autoSize;
    }

    protected boolean isVisiable() {
        return this.visiable;
    }

    protected void setVisiable(boolean visiable) {
        this.visiable = visiable;
    }
}

