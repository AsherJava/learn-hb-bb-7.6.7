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

public class AddAction
extends AbstractAction {
    @JsonCreator
    public AddAction(@JsonProperty(value="name") String name, @JsonProperty(value="selections") GridRegion[] selections, @JsonProperty(value="params") Object params) {
        super(name, selections, params);
    }

    protected void addRow(Grid2Data grid, GridRegion region, int count, boolean forward) {
        int index = 0;
        index = null == region ? grid.getRowCount() - 1 : region.getY() + region.getHeight() - 1;
        if (!forward) {
            ++index;
        }
        int copyIndex = index - 1;
        if (1 == index) {
            copyIndex = 1;
        }
        grid.insertRows(index, count, copyIndex);
    }

    protected void addCol(Grid2Data grid, GridRegion region, int count, boolean forward) {
        int index = 0;
        index = null == region ? grid.getColumnCount() - 1 : region.getX() + region.getWidth() - 1;
        if (!forward) {
            ++index;
        }
        int copyIndex = index - 1;
        if (1 == index) {
            copyIndex = 1;
        }
        grid.insertColumns(index, count, copyIndex);
    }

    @Override
    public void run(Grid2Data grid) {
        for (GridRegion region : this.getSelections()) {
            if (this.isAllCol(grid, region)) {
                this.addCol(grid, region, 1, true);
                continue;
            }
            if (this.isAllRow(grid, region)) {
                this.addRow(grid, region, 1, true);
                continue;
            }
            this.addCol(grid, region, 1, true);
        }
    }
}

