/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.area;

import com.jiuqi.bi.quickreport.engine.area.GridArea;

public final class FixedArea
extends GridArea {
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.getSheetName()).append(':').append(this.getCells());
        return buffer.toString();
    }
}

