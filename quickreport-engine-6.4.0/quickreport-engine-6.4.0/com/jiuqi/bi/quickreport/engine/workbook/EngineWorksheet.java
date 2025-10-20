/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 */
package com.jiuqi.bi.quickreport.engine.workbook;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.ResultGridData;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorksheet;
import com.jiuqi.bi.quickreport.model.WorksheetModel;

public final class EngineWorksheet
extends ReportWorksheet {
    private WorksheetModel sheetModel;
    private ResultGridData resultGrid;

    public EngineWorksheet(WorksheetModel sheetModel) {
        this.sheetModel = sheetModel;
        GridData newGrid = (GridData)sheetModel.getGriddata().clone();
        this.resultGrid = new ResultGridData(newGrid);
    }

    @Override
    public String name() {
        return this.sheetModel.getName();
    }

    @Override
    public GridData getGridData() {
        return this.sheetModel.getGriddata();
    }

    public WorksheetModel getSheetModel() {
        return this.sheetModel;
    }

    public ResultGridData getResultGrid() {
        return this.resultGrid;
    }
}

