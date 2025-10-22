/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.sync.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.designer.sync.AbstractAction;
import com.jiuqi.nr.designer.sync.entity.GridRegion;
import com.jiuqi.nvwa.grid2.Grid2Data;

public class HiddenAction
extends AbstractAction {
    @JsonCreator
    public HiddenAction(@JsonProperty(value="name") String name, @JsonProperty(value="selections") GridRegion[] selections, @JsonProperty(value="params") Object params) {
        super(name, selections, params);
    }

    protected void hiddenRow(Grid2Data grid, GridRegion region) {
        if (region.getY() < grid.getRowCount()) {
            grid.setRowHidden(region.getY(), true);
        }
    }

    protected void showHiddenRow(Grid2Data grid, GridRegion region) {
        for (int i = 1; i < grid.getRowCount(); ++i) {
            if (!grid.isRowHidden(i)) continue;
            grid.setRowHidden(i, false);
        }
    }

    protected void hiddenCol(Grid2Data grid, GridRegion region) {
        if (region.getX() < grid.getColumnCount()) {
            grid.setColumnHidden(region.getX(), true);
        }
    }

    protected void showHiddenCol(Grid2Data grid, GridRegion region) {
        for (int i = 1; i < grid.getColumnCount(); ++i) {
            if (!grid.isColumnHidden(i)) continue;
            grid.setColumnHidden(i, false);
        }
    }

    protected void showHiddenRowTitle(Grid2Data grid, GridRegion region) {
        if (grid.isColumnHidden(0)) {
            grid.setColumnHidden(0, false);
        } else {
            grid.setColumnHidden(0, true);
        }
    }

    protected void showHiddenColTitle(Grid2Data grid, GridRegion region) {
        if (grid.isRowHidden(0)) {
            grid.setRowHidden(0, false);
        } else {
            grid.setRowHidden(0, true);
        }
    }

    @Override
    public void run(Grid2Data grid) {
    }
}

