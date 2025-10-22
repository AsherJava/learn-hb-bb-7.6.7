/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.designer.sync.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.designer.sync.AbstractAction;
import com.jiuqi.nr.designer.sync.entity.GridRegion;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.Set;

public class UnMergeAction
extends AbstractAction {
    @JsonCreator
    public UnMergeAction(@JsonProperty(value="name") String name, @JsonProperty(value="selections") GridRegion[] selections, @JsonProperty(value="params") Object params) {
        super(name, selections, params);
    }

    @Override
    public void run(Grid2Data grid) {
        for (GridRegion region : this.getSelections()) {
            Set<GridCellData> allCells = region.getAllCells(grid);
            for (GridCellData cell : allCells) {
                grid.unmergeCell(cell.getColIndex(), cell.getRowIndex());
            }
        }
    }
}

