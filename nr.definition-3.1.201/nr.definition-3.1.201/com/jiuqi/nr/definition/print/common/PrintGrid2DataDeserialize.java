/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.Grid2FieldList
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize
 */
package com.jiuqi.nr.definition.print.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.Grid2FieldList;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize;
import java.io.IOException;

public class PrintGrid2DataDeserialize
extends Grid2DataDeserialize {
    public Grid2Data deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        int i;
        Grid2Data grid = super.deserialize(p, ctxt);
        int rowCount = grid.getRowCount();
        int columnCount = grid.getColumnCount();
        Grid2FieldList merges = grid.merges();
        if (0 != merges.count()) {
            for (i = merges.count() - 1; i == 0; --i) {
                merges.remove(i);
            }
        }
        for (i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                GridCellData gridCellData = grid.getGridCellData(j, i);
                int col = gridCellData.getColIndex();
                int row = gridCellData.getRowIndex();
                int width = gridCellData.getColSpan();
                int height = gridCellData.getRowSpan();
                if (!gridCellData.isMerged()) continue;
                Grid2CellField grid2CellField = new Grid2CellField(col, row, width + col - 1, height + row - 1);
                merges.addMergeRect(grid2CellField);
            }
        }
        return grid;
    }
}

