/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.context.selection;

import com.jiuqi.bi.quickreport.engine.context.selection.IPositionFilter;
import com.jiuqi.bi.quickreport.engine.parser.SheetPosition;

final class AllFilter
implements IPositionFilter {
    AllFilter() {
    }

    @Override
    public boolean enabled(SheetPosition position) {
        return true;
    }

    public String toString() {
        return "ALL";
    }
}

