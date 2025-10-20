/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat;

public class StatConfig
implements Cloneable {
    public boolean isCreateTimepoint = true;
    public boolean isNeedSort = false;
    public boolean isAutoFillParentColumn = false;

    public StatConfig() {
    }

    public StatConfig(boolean isNeedSort, boolean isAutoFillParentColumn) {
        this.isNeedSort = isNeedSort;
        this.isAutoFillParentColumn = isAutoFillParentColumn;
    }

    public StatConfig clone() {
        try {
            return (StatConfig)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

