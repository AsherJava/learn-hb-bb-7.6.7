/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.h5.H5GridCell
 *  com.jiuqi.bi.grid.h5.H5GridGenerator
 */
package com.jiuqi.bi.quickreport.h5;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.h5.H5GridCell;
import com.jiuqi.bi.grid.h5.H5GridGenerator;
import com.jiuqi.bi.quickreport.h5.IReportH5GridOptionProvider;
import com.jiuqi.bi.quickreport.h5.ReportH5GridCell;
import java.util.ArrayList;
import java.util.List;

public class ReportH5GridGenerator
extends H5GridGenerator {
    private IReportH5GridOptionProvider provider;

    public ReportH5GridGenerator(GridData gridData) {
        this(gridData, null);
    }

    public ReportH5GridGenerator(GridData gridData, IReportH5GridOptionProvider provider) {
        super(gridData);
        this.provider = provider;
    }

    public List<List<H5GridCell>> getRows() {
        ArrayList<List<H5GridCell>> rows = new ArrayList<List<H5GridCell>>();
        GridData gridData = this.getGridData();
        for (int i = 0; i < gridData.getRowCount(); ++i) {
            ArrayList<ReportH5GridCell> row = new ArrayList<ReportH5GridCell>();
            int lastHideRow = this.getLastHideRow(i);
            for (int j = 0; j < gridData.getColCount(); ++j) {
                GridCell cellEx = gridData.getCellEx(j, i);
                int lastHideCol = this.getLastHideCol(j);
                if (j != lastHideCol || i != lastHideRow) {
                    GridCell lastHideCellEx = gridData.getCellEx(lastHideCol, lastHideRow);
                    if (j != lastHideCol) {
                        cellEx.setREdge(lastHideCellEx.getREdgeColor(), lastHideCellEx.getREdgeStyle());
                    }
                    if (i != lastHideRow) {
                        cellEx.setBEdge(lastHideCellEx.getBEdgeColor(), lastHideCellEx.getBEdgeStyle());
                    }
                }
                row.add(new ReportH5GridCell(cellEx, gridData, this.provider));
            }
            rows.add(row);
        }
        return rows;
    }

    public List<Integer> getColWidths() {
        List colWidths = super.getColWidths();
        if (colWidths.size() > 0) {
            int w = this.getREdgeWidth_col0();
            colWidths.set(0, w);
        }
        return colWidths;
    }

    private int getREdgeWidth_col0() {
        int wMax = 0;
        for (int i = 1; i < this.getGridData().getRowCount(); ++i) {
            GridCell cell = this.getGridData().getCell(0, i);
            int edgeStyle = ReportH5GridCell.getH5EdgeStyle(cell.getREdgeStyle());
            int w = this.getEdgeWidth(edgeStyle);
            if (w <= wMax) continue;
            wMax = w;
        }
        return wMax;
    }

    public List<Integer> getRowHeights() {
        List rowHeights = super.getRowHeights();
        if (rowHeights.size() > 0) {
            int w = this.getBEdgeWidth_row0();
            rowHeights.set(0, w);
        }
        return rowHeights;
    }

    private int getBEdgeWidth_row0() {
        int wMax = 0;
        for (int i = 1; i < this.getGridData().getColCount(); ++i) {
            GridCell cell = this.getGridData().getCell(i, 0);
            int edgeStyle = ReportH5GridCell.getH5EdgeStyle(cell.getBEdgeStyle());
            int w = this.getEdgeWidth(edgeStyle);
            if (w <= wMax) continue;
            wMax = w;
        }
        return wMax;
    }

    private int getEdgeWidth(int edgeStyle) {
        int w = 0;
        switch (edgeStyle) {
            case -1: {
                w = 1;
                break;
            }
            case 0: {
                w = 0;
                break;
            }
            case 1: {
                w = 1;
                break;
            }
            case 2: {
                w = 1;
                break;
            }
            case 4: {
                w = 2;
                break;
            }
            case 8: {
                w = 3;
                break;
            }
            case 16: {
                w = 0;
                break;
            }
            default: {
                w = 1;
            }
        }
        return w;
    }
}

