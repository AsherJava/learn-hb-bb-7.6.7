/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.single.core.para.parser.print;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.consts.EdgeType;
import com.jiuqi.nr.single.core.para.parser.print.CustomGraph;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GridPrintPage
extends CustomGraph {
    private List<Integer> prtCols = new ArrayList<Integer>();
    private List<Integer> prtRows = new ArrayList<Integer>();
    private List<Integer> prtWidths = new ArrayList<Integer>();
    private List<Integer> prtHeights = new ArrayList<Integer>();
    private List<Byte> topMostEdge = new ArrayList<Byte>();
    private List<Byte> leftMostEdge = new ArrayList<Byte>();
    private int saveIndex = 0;
    private Grid2Data privateGridData;

    public Grid2Data getGridData() {
        return this.privateGridData;
    }

    public void setGridData(Grid2Data value) {
        this.privateGridData = value;
    }

    public List<Integer> getPrtCols() {
        return this.prtCols;
    }

    public List<Integer> getPrtRows() {
        return this.prtRows;
    }

    public List<Integer> getPrtWidths() {
        return this.prtWidths;
    }

    public List<Integer> getPrtHeights() {
        return this.prtHeights;
    }

    @Override
    public final void loadFromStream(Stream stream) throws IOException, StreamException {
        int i;
        super.loadFromStream(stream);
        int total = ReadUtil.readIntValue(stream);
        for (i = 0; i < total; ++i) {
            this.prtCols.add(Integer.valueOf(ReadUtil.readShortValue(stream)));
        }
        total = ReadUtil.readIntValue(stream);
        for (i = 0; i < total; ++i) {
            this.prtWidths.add(Integer.valueOf(ReadUtil.readShortValue(stream)));
        }
        total = ReadUtil.readIntValue(stream);
        for (i = 0; i < total; ++i) {
            this.prtRows.add(Integer.valueOf(ReadUtil.readShortValue(stream)));
        }
        total = ReadUtil.readIntValue(stream);
        for (i = 0; i < total; ++i) {
            this.prtHeights.add(Integer.valueOf(ReadUtil.readShortValue(stream)));
        }
        this.saveIndex = ReadUtil.readIntValue(stream);
        this.DeleteInvalidColRow();
        this.UpdateTopLeftEdges();
    }

    private void DeleteInvalidColRow() {
        int i;
        if (this.getGridData() == null) {
            return;
        }
        int maxColNum = this.getGridData().getColumnCount() - 1;
        int maxRowNum = this.getGridData().getRowCount() - 1;
        for (i = this.prtCols.size() - 1; i >= 0; --i) {
            if (this.prtCols.get(i) <= maxColNum) continue;
            this.prtCols.remove(i);
            this.prtWidths.remove(i);
        }
        for (i = this.prtRows.size() - 1; i >= 0; --i) {
            if (this.prtRows.get(i) <= maxRowNum) continue;
            this.prtRows.remove(i);
            this.prtHeights.remove(i);
        }
    }

    private void UpdateTopLeftEdges() {
        block9: {
            int x;
            block10: {
                int i;
                this.topMostEdge.clear();
                this.leftMostEdge.clear();
                if (this.prtCols.size() <= 0 || this.prtRows.size() <= 0) break block9;
                int y = this.prtRows.get(0);
                if (--y < 0) {
                    for (i = 0; i < this.prtCols.size(); ++i) {
                        this.leftMostEdge.add((byte)EdgeType.ETSINGLE.getValue());
                    }
                } else if (this.getGridData() != null) {
                    for (i = 0; i < this.prtCols.size(); ++i) {
                        GridCellData cell = this.getGridData().getGridCellData(this.prtCols.get(i).intValue(), y);
                        if (cell == null) {
                            this.leftMostEdge.add((byte)EdgeType.ETSINGLE.getValue());
                            continue;
                        }
                        this.leftMostEdge.add((byte)cell.getRightBorderStyle());
                    }
                }
                x = this.prtCols.get(0);
                if (--x >= 0) break block10;
                for (int i2 = 0; i2 < this.prtRows.size(); ++i2) {
                    this.topMostEdge.add((byte)EdgeType.ETSINGLE.getValue());
                }
                break block9;
            }
            if (this.getGridData() == null) break block9;
            for (int i = 0; i < this.prtRows.size(); ++i) {
                GridCellData cell = this.getGridData().getGridCellData(x, this.prtRows.get(i).intValue());
                if (cell == null) {
                    this.topMostEdge.add((byte)EdgeType.ETSINGLE.getValue());
                    continue;
                }
                this.topMostEdge.add((byte)cell.getBottomBorderStyle());
            }
        }
    }
}

