/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.area;

import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import java.util.ArrayList;
import java.util.List;

public final class RestrictedCell {
    private CellBindingInfo cell;
    private List<ExpandingRegion> rowRestrictions = new ArrayList<ExpandingRegion>();
    private List<ExpandingRegion> colRestrictions = new ArrayList<ExpandingRegion>();

    public CellBindingInfo getCell() {
        return this.cell;
    }

    public void setCell(CellBindingInfo cell) {
        this.cell = cell;
    }

    public List<ExpandingRegion> getRowRestrictions() {
        return this.rowRestrictions;
    }

    public List<ExpandingRegion> getColRestrictions() {
        return this.colRestrictions;
    }

    public boolean isSameRestrictions(RestrictedCell cell) {
        return RestrictedCell.equals(this.rowRestrictions, cell.rowRestrictions) && RestrictedCell.equals(this.colRestrictions, cell.colRestrictions);
    }

    public static boolean equals(List<ExpandingRegion> restrictions1, List<ExpandingRegion> restrictions2) {
        if (restrictions1.size() != restrictions2.size()) {
            return false;
        }
        for (int i = 0; i < restrictions1.size(); ++i) {
            if (restrictions1.get(i) == restrictions2.get(i)) continue;
            return false;
        }
        return true;
    }
}

