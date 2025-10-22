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

public class DeleteAction
extends AbstractAction {
    @JsonCreator
    public DeleteAction(@JsonProperty(value="name") String name, @JsonProperty(value="selections") GridRegion[] selections, @JsonProperty(value="params") Object params) {
        super(name, selections, params);
    }

    protected void deleteRow(Grid2Data grid, GridRegion region) {
        if (region.getY() < grid.getRowCount() && region.getY() > 0) {
            int count = region.getHeight();
            if (region.getY() + region.getHeight() - 1 >= grid.getRowCount()) {
                count = grid.getRowCount() - region.getY();
            }
            grid.deleteRows(region.getY(), count);
        }
    }

    protected void deleteCol(Grid2Data grid, GridRegion region) {
        if (region.getX() > 0 && region.getX() < grid.getColumnCount()) {
            int count = region.getWidth();
            if (region.getX() + region.getWidth() - 1 >= grid.getColumnCount()) {
                count = grid.getColumnCount() - region.getX();
            }
            grid.deleteColumns(region.getX(), count);
        }
    }

    @Override
    public void run(Grid2Data grid) {
        for (GridRegion region : this.getSelections()) {
            if (this.isAllCol(grid, region)) {
                this.deleteCol(grid, region);
                continue;
            }
            if (!this.isAllRow(grid, region)) continue;
            this.deleteRow(grid, region);
        }
    }
}

