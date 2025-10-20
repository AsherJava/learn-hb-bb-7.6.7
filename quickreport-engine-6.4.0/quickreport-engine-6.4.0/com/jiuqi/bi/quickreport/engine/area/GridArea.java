/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.area;

import com.jiuqi.bi.quickreport.engine.area.ReportAreaExpcetion;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class GridArea {
    private String sheetName;
    protected final List<CellBindingInfo> cells = new ArrayList<CellBindingInfo>();
    public final Set<GridArea> _depends = new HashSet<GridArea>();
    public final Set<GridArea> _affects = new HashSet<GridArea>();
    public int _indegree;

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<CellBindingInfo> getCells() {
        return this.cells;
    }

    public void validate() throws ReportAreaExpcetion {
    }

    public static void linkAreas(GridArea prev, GridArea next) {
        prev._affects.add(next);
        next._depends.add(prev);
    }
}

