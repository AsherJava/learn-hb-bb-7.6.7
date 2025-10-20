/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.layer;

import com.jiuqi.bi.quickreport.engine.area.GridArea;
import java.util.ArrayList;
import java.util.List;

public final class CalcLayer {
    private List<GridArea> areas = new ArrayList<GridArea>();

    public List<GridArea> getAreas() {
        return this.areas;
    }

    public String toString() {
        return this.areas.toString();
    }
}

