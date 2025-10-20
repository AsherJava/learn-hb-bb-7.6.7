/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.writeback.workbook;

import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import java.util.ArrayList;
import java.util.List;

public final class WritebackRestrictions {
    private ExpandingArea masterArea;
    private AxisDataNode[] colRestrictions;
    private AxisDataNode[] rowRestrictions;

    public WritebackRestrictions(ExpandingArea area) {
        this.masterArea = area;
    }

    public List<AxisDataNode> getAllRestrictions() {
        ArrayList<AxisDataNode> restrictions = new ArrayList<AxisDataNode>();
        if (this.rowRestrictions != null) {
            for (AxisDataNode data : this.rowRestrictions) {
                restrictions.add(data);
            }
        }
        if (this.colRestrictions != null) {
            for (AxisDataNode data : this.colRestrictions) {
                restrictions.add(data);
            }
        }
        return restrictions;
    }

    public AxisDataNode[] getColRestrictions() {
        return this.colRestrictions;
    }

    public void setColRestrictions(AxisDataNode[] colRestrictions) {
        this.colRestrictions = colRestrictions;
    }

    public AxisDataNode[] getRowRestrictions() {
        return this.rowRestrictions;
    }

    public void setRowRestrictions(AxisDataNode[] rowRestrictions) {
        this.rowRestrictions = rowRestrictions;
    }

    public ExpandingArea getMasterArea() {
        return this.masterArea;
    }
}

