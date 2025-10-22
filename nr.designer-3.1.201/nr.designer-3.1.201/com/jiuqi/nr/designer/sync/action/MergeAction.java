/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$TextAlignment
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.designer.sync.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.designer.sync.AbstractAction;
import com.jiuqi.nr.designer.sync.entity.GridRegion;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import org.apache.commons.lang3.StringUtils;

public class MergeAction
extends AbstractAction {
    @JsonCreator
    public MergeAction(@JsonProperty(value="name") String name, @JsonProperty(value="selections") GridRegion[] selections, @JsonProperty(value="params") Object params) {
        super(name, selections, params);
    }

    protected void mergeAndClean(Grid2Data grid, GridRegion region) {
        GridCellData gridCellData;
        String showText = "";
        for (int i = 0; i < region.getWidth(); ++i) {
            for (int j = 0; j < region.getHeight(); ++j) {
                gridCellData = grid.getGridCellData(region.getX() + i, region.getY() + j);
                if (StringUtils.isEmpty((CharSequence)showText)) {
                    showText = gridCellData.getShowText();
                }
                gridCellData.setShowText(null);
            }
        }
        gridCellData = grid.getGridCellData(region.getX(), region.getY());
        gridCellData.setShowText(showText);
        gridCellData.setHorzAlign(GridEnums.getIntValue((Enum)GridEnums.TextAlignment.Center));
        this.merge(grid, region);
    }

    protected void merge(Grid2Data grid, GridRegion region) {
        grid.mergeCells(region.getX(), region.getY(), region.getX() + region.getWidth() - 1, region.getY() + region.getHeight() - 1);
    }

    @Override
    public void run(Grid2Data grid) {
        for (GridRegion region : this.getSelections()) {
            this.mergeAndClean(grid, region);
        }
    }
}

