/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.designer.sync.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.designer.sync.action.AddAction;
import com.jiuqi.nr.designer.sync.entity.GridRegion;
import com.jiuqi.nvwa.grid2.Grid2Data;
import org.apache.commons.lang3.StringUtils;

public class PasteAction
extends AddAction {
    private String params;

    @JsonCreator
    public PasteAction(@JsonProperty(value="name") String name, @JsonProperty(value="selections") GridRegion[] selections, @JsonProperty(value="params") String params) {
        super(name, selections, params);
        this.params = params;
    }

    @Override
    public void run(Grid2Data grid) {
        int addRowCount;
        GridRegion region = this.getSelections()[this.getSelections().length - 1];
        if (StringUtils.isEmpty((CharSequence)this.params)) {
            return;
        }
        int rowCount = 1;
        int colCount = 1;
        String[] rows = this.params.split("\n");
        rowCount = rows.length;
        for (String row : rows) {
            int c;
            if (StringUtils.isEmpty((CharSequence)row) || (c = row.split("\t").length) <= colCount) continue;
            colCount = c;
        }
        int addColCount = region.getX() + colCount - grid.getColumnCount();
        if (addColCount > 0) {
            this.addCol(grid, null, addColCount, false);
        }
        if ((addRowCount = region.getY() + rowCount - grid.getRowCount()) > 0) {
            this.addRow(grid, null, addColCount, false);
        }
    }
}

