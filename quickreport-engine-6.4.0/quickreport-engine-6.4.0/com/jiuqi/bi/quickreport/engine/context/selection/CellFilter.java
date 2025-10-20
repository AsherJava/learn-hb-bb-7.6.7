/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.bi.quickreport.engine.context.selection;

import com.jiuqi.bi.quickreport.engine.context.selection.IPositionFilter;
import com.jiuqi.bi.quickreport.engine.parser.SheetPosition;
import com.jiuqi.bi.syntax.cell.Position;

class CellFilter
implements IPositionFilter {
    private final String sheetName;
    private final Position position;

    public CellFilter(String sheetName, Position position) {
        this.sheetName = sheetName;
        this.position = position;
    }

    @Override
    public boolean enabled(SheetPosition position) {
        return this.sheetName.equalsIgnoreCase(position.getSheetName()) && this.position.equals((Object)position.getPosition());
    }

    public String toString() {
        return this.sheetName + "!" + this.position;
    }
}

