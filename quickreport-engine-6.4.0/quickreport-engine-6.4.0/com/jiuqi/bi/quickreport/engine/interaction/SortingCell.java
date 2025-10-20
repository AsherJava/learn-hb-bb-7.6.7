/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.interaction;

import com.jiuqi.bi.quickreport.model.OrderMode;

public final class SortingCell {
    public final OrderMode mode;
    public final String expression;
    public final int srcID;
    public final String groupName;

    public SortingCell(OrderMode mode, String expression, int srcID, String groupName) {
        this.mode = mode;
        this.expression = expression;
        this.srcID = srcID;
        this.groupName = groupName;
    }

    public String toString() {
        return (Object)((Object)this.mode) + ":" + this.expression;
    }
}

