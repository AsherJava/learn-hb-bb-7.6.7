/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.bi.quickreport.engine.context.selection;

import com.jiuqi.bi.quickreport.engine.context.selection.IPositionFilter;
import com.jiuqi.bi.quickreport.engine.parser.SheetPosition;
import com.jiuqi.bi.syntax.cell.Region;

final class RegionFilter
implements IPositionFilter {
    private final String sheetName;
    private final Region region;

    public RegionFilter(String sheetName, Region region) {
        this.sheetName = sheetName;
        this.region = region;
    }

    @Override
    public boolean enabled(SheetPosition position) {
        return this.sheetName.equalsIgnoreCase(position.getSheetName()) && this.region.contains(position.getPosition());
    }

    public String toString() {
        return this.sheetName + "!" + this.region;
    }
}

